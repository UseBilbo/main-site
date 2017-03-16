/*
 * Copyright (c) 2015 The original author or authors
 * ---------------------------------
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 and Apache
 * License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package com.usebilbo.vertx.cluster.manager;

import static org.apache.ignite.events.EventType.EVT_NODE_FAILED;
import static org.apache.ignite.events.EventType.EVT_NODE_JOINED;
import static org.apache.ignite.events.EventType.EVT_NODE_LEFT;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteAtomicLong;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteQueue;
import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.apache.ignite.events.DiscoveryEvent;

import com.usebilbo.vertx.annotation.ClusterManagerQueue;
import com.usebilbo.vertx.cluster.manager.impl.AsyncMapImpl;
import com.usebilbo.vertx.cluster.manager.impl.AsyncMultiMapImpl;
import com.usebilbo.vertx.cluster.manager.impl.MapImpl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxException;
import io.vertx.core.impl.ContextImpl;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.Counter;
import io.vertx.core.shareddata.Lock;
import io.vertx.core.spi.cluster.AsyncMultiMap;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.core.spi.cluster.NodeListener;

/**
 * Apache Ignite based cluster manager.
 *
 * @author Andrey Gura
 * @author siy
 */
@Singleton
public class IgniteClusterManager implements ClusterManager {
    private static final Logger log = LoggerFactory.getLogger(IgniteClusterManager.class);

    private final Queue<String> pendingLocks = new ConcurrentLinkedQueue<>();
    private final Object monitor = new Object();
    private final Provider<Ignite> provider;
    private final CollectionConfiguration collectionConfiguration;
    
    private Vertx vertx;

    private NodeListener nodeListener;

    private volatile boolean active;


    @Inject
    public IgniteClusterManager(Provider<Ignite> provider, @ClusterManagerQueue CollectionConfiguration collectionConfiguration) {
        this.provider = provider;
        this.collectionConfiguration = collectionConfiguration;
    }

    @Override
    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void nodeListener(NodeListener nodeListener) {
        this.nodeListener = nodeListener;
    }

    @Override
    public <K, V> void getAsyncMultiMap(String name, Handler<AsyncResult<AsyncMultiMap<K, V>>> handler) {
        vertx.executeBlocking(fut -> fut.complete(new AsyncMultiMapImpl<>(this.<K, List<V>>getCache(name), vertx)),
                handler);
    }

    @Override
    public <K, V> void getAsyncMap(String name, Handler<AsyncResult<AsyncMap<K, V>>> handler) {
        vertx.executeBlocking(fut -> fut.complete(new AsyncMapImpl<>(getCache(name), vertx)), handler);
    }

    @Override
    public <K, V> Map<K, V> getSyncMap(String name) {
        return new MapImpl<>(getCache(name));
    }

    @Override
    public void getLockWithTimeout(String name, long timeout, Handler<AsyncResult<Lock>> handler) {
        ContextImpl context = (ContextImpl) vertx.getOrCreateContext();
        // Ordered on the internal blocking executor
        context.executeBlocking(() -> {
            boolean locked = false;

            try {
                @SuppressWarnings("resource")
                IgniteQueue<String> queue = getQueue(name, true);

                pendingLocks.offer(name);

                locked = queue.offer(getNodeID(), timeout, TimeUnit.MILLISECONDS);

                if (!locked) {
                    // EVT_NODE_LEFT/EVT_NODE_FAILED event might be already handled, so trying get lock again if
                    // node left topology.
                    // Use IgniteSempahore when it will be fixed.
                    String ownerId = queue.peek();
                    ClusterNode ownerNode = ignite().cluster().forNodeId(UUID.fromString(ownerId)).node();
                    if (ownerNode == null) {
                        queue.remove(ownerId);
                        locked = queue.offer(getNodeID(), timeout, TimeUnit.MILLISECONDS);
                    }
                }
            } catch (Exception e) {
                throw new VertxException("Error during getting lock " + name, e);
            } finally {
                pendingLocks.remove(name);
            }

            if (locked) {
                return new LockImpl(name);
            } else {
                throw new VertxException("Timed out waiting to get lock " + name);
            }
        }, handler);
    }

    @Override
    public void getCounter(String name, Handler<AsyncResult<Counter>> handler) {
        vertx.executeBlocking(fut -> fut.complete(new CounterImpl(ignite().atomicLong(name, 0, true))), handler);
    }

    @Override
    public String getNodeID() {
        return nodeId(ignite().cluster().localNode());
    }

    @Override
    public List<String> getNodes() {
        return ignite().cluster().nodes().stream().map(IgniteClusterManager::nodeId).collect(Collectors.toList());
    }

    @Override
    public void join(Handler<AsyncResult<Void>> handler) {
        synchronized (monitor) {
            vertx.executeBlocking(fut -> {
                if (!active) {
                    active = true;

                    ignite().events().localListen(event -> {
                        if (!active) {
                            return false;
                        }

                        if (nodeListener != null) {
                            vertx.executeBlocking(f -> {
                                if (isActive()) {
                                    switch (event.type()) {
                                    case EVT_NODE_JOINED:
                                        nodeListener.nodeAdded(nodeId(((DiscoveryEvent) event).eventNode()));
                                        break;
                                    case EVT_NODE_LEFT:
                                    case EVT_NODE_FAILED:
                                        String nodeId = nodeId(((DiscoveryEvent) event).eventNode());
                                        nodeListener.nodeLeft(nodeId);
                                        releasePendingLocksForFailedNode(nodeId);
                                        break;
                                    }
                                }
                                fut.complete();
                            }, null);
                        }

                        return true;
                    }, EVT_NODE_JOINED, EVT_NODE_LEFT, EVT_NODE_FAILED);

                    fut.complete();
                }
            }, handler);
        }
    }

    private Ignite ignite() {
        return provider.get();
    }

    /**
     * @param nodeId
     *            ID of node that left topology
     */
    private void releasePendingLocksForFailedNode(final String nodeId) {
        Set<String> processed = new HashSet<>();

        pendingLocks.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                if (processed.add(name)) {
                    @SuppressWarnings("resource")
                    IgniteQueue<String> queue = getQueue(name, false);

                    if (queue != null && nodeId.equals(queue.peek())) {
                        queue.remove(nodeId);
                    }
                }
            }
        });
    }

    //TODO: move Ignite lifecycle control out of this class

    @Override
    public void leave(Handler<AsyncResult<Void>> handler) {
        synchronized (monitor) {
            vertx.executeBlocking(fut -> {
                if (active) {
                    active = false;
                    try {
                        ignite().close();
                    } catch (Exception e) {
                        log.error(e);
                    }
                }

                fut.complete();
            }, handler);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    private <K, V> IgniteCache<K, V> getCache(String name) {
        return ignite().getOrCreateCache(name);
    }

    private <T> IgniteQueue<T> getQueue(String name, boolean create) {
        return ignite().queue(name, 1, create ? collectionConfiguration : null);
    }

    private static String nodeId(ClusterNode node) {
        return node.id().toString();
    }

    private class LockImpl implements Lock {
        private final String name;

        private LockImpl(String name) {
            this.name = name;
        }

        @SuppressWarnings("resource")
        @Override
        public void release() {
            vertx.executeBlocking(future -> {
                IgniteQueue<String> queue = getQueue(name, true);
                String ownerId = queue.poll();

                if (ownerId == null) {
                    throw new VertxException("Inconsistent lock state " + name);
                }
                future.complete();
            }, false, null);
        }
    }

    private class CounterImpl implements Counter {
        private final IgniteAtomicLong cnt;

        private CounterImpl(IgniteAtomicLong cnt) {
            this.cnt = cnt;
        }

        @Override
        public void get(Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.get()), handler);
        }

        @Override
        public void incrementAndGet(Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.incrementAndGet()), handler);
        }

        @Override
        public void getAndIncrement(Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.getAndIncrement()), handler);
        }

        @Override
        public void decrementAndGet(Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.decrementAndGet()), handler);
        }

        @Override
        public void addAndGet(long value, Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.addAndGet(value)), handler);
        }

        @Override
        public void getAndAdd(long value, Handler<AsyncResult<Long>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.getAndAdd(value)), handler);
        }

        @Override
        public void compareAndSet(long expected, long value, Handler<AsyncResult<Boolean>> handler) {
            Objects.requireNonNull(handler, "handler");
            vertx.executeBlocking(fut -> fut.complete(cnt.compareAndSet(expected, value)), handler);
        }
    }
}
