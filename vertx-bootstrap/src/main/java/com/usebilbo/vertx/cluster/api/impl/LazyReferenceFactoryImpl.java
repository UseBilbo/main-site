package com.usebilbo.vertx.cluster.api.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.IgniteEvents;
import org.apache.ignite.IgniteFileSystem;
import org.apache.ignite.IgniteQueue;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.CollectionConfiguration;

import com.usebilbo.vertx.cluster.api.ClusterFileSystem;
import com.usebilbo.vertx.cluster.api.IgniteProvider;
import com.usebilbo.vertx.cluster.api.LazyReference;
import com.usebilbo.vertx.cluster.api.LazyReferenceFactory;
import com.usebilbo.vertx.cluster.api.TransactionManager;

@Singleton
public class LazyReferenceFactoryImpl implements LazyReferenceFactory {
    private final IgniteProvider provider;
    
    @Inject
    public LazyReferenceFactoryImpl(IgniteProvider provider) {
        this.provider = provider;
    }

    @Override
    public <K, V> LazyReference<IgniteCache<K, V>> cache(String name) {
        return new AbstractReference<>(provider, (p) -> p.get().getOrCreateCache(name));
    }

    @Override
    public <K, V> LazyReference<IgniteCache<K, V>> cache(CacheConfiguration<K, V> config) {
        return new AbstractReference<>(provider, (p) -> p.get().getOrCreateCache(config));
    }

    @Override
    public LazyReference<IgniteAtomicSequence> sequence(String name) {
        return new AbstractReference<>(provider, (p) -> p.get().atomicSequence(name, 0, true));
    }

    @Override
    public LazyReference<IgniteAtomicSequence> sequence(String name, long initialValue) {
        return new AbstractReference<>(provider, (p) -> p.get().atomicSequence(name, initialValue, true));
    }
    
    @Override
    public LazyReference<IgniteFileSystem> fs(ClusterFileSystem fileSystem) {
        return new AbstractReference<>(provider, (p) -> p.get().fileSystem(fileSystem.label()));
    }

    @Override
    public <T> LazyReference<IgniteQueue<T>> queueSmall(String name) {
        CollectionConfiguration config = new CollectionConfiguration();
        config.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        config.setCacheMode(CacheMode.PARTITIONED);
        config.setCollocated(true);
        return new AbstractReference<>(provider, (p) -> p.get().queue(name, 0, config));
    }

    @Override
    public <T> LazyReference<IgniteQueue<T>> queueLarge(String name) {
        CollectionConfiguration config = new CollectionConfiguration();
        config.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        config.setCacheMode(CacheMode.PARTITIONED);
        config.setCollocated(false);
        return new AbstractReference<>(provider, (p) -> p.get().queue(name, 0, config));
    }

    @Override
    public <T> LazyReference<IgniteQueue<T>> queue(String name, int capacity, CollectionConfiguration configuration) {
        return new AbstractReference<>(provider, (p) -> p.get().queue(name, capacity, configuration));
    }

    @Override
    public LazyReference<TransactionManager> tm() {
        return new AbstractReference<>(provider, (p) -> new TransactionManagerImpl(p));
    }
    
    @Override
    public LazyReference<IgniteCompute> compute() {
        return new AbstractReference<>(provider, (p) -> p.get().compute());
    }

    @Override
    public LazyReference<IgniteCompute> compute(ClusterGroup group) {
        return new AbstractReference<>(provider, (p) -> p.get().compute(group));
    }

    @Override
    public LazyReference<IgniteEvents> events() {
        return new AbstractReference<>(provider, (p) -> p.get().events());
    }
}
