package com.usebilbo.vertx.cluster.configuration.mvstore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.cache.configuration.Factory;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.ignite.cache.store.CacheStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.mvstore.MVStore;

import com.usebilbo.vertx.cluster.CacheStoreFactoryProvider;
import com.usebilbo.vertx.properties.PropertyContainer;
import com.usebilbo.vertx.util.ListUtils;

@Singleton
public class MVStoreCacheStoreFactoryProvider implements CacheStoreFactoryProvider {
    private static final int DEFAULT_FILL_RATE = 50;
    private static final int DEFAULT_WRITE_SIZE = 4*1024*1024;

    private static final Logger LOG = LogManager.getLogger();

    private final MVStore store;
    private final ScheduledExecutorService service;
    private final int fillRate;
    private final int writeSize;
    private final long compactionInterval;

    @Inject
    public MVStoreCacheStoreFactoryProvider(@Named("vertx.cluster.storage.path") PropertyContainer storage,
                                            @Named("vertx.cluster.storage.compaction.interval") PropertyContainer interval,
                                            @Named("vertx.cluster.storage.compaction.fill.rate") PropertyContainer fillRate,
                                            @Named("vertx.cluster.storage.compaction.write.size") PropertyContainer writeSize) {
        this.fillRate = fillRate.asInt(DEFAULT_FILL_RATE);
        this.writeSize = writeSize.asInt(DEFAULT_WRITE_SIZE);
        this.compactionInterval = interval.asDuration();

        String storageFile = ListUtils.last(storage.list());
        LOG.info("Opening persistent storage at {}", storageFile);
        
        store = new MVStore.Builder().compressHigh().fileName(storageFile).open();
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(() -> compact(), compactionInterval, compactionInterval, TimeUnit.MILLISECONDS);
    }
    
    private void compact() {
        LOG.info("Compaction is started");
        LOG.info("Compaction is done ({})", store.compact(fillRate, writeSize));
    }

    @Override
    public <K, V> Factory<? extends CacheStore<? super K, ? super V>> get(String name, Class<K> keyType, Class<V> valueType) {
        return new MVStoreCacheStoreFactory<>(store, name, keyType, valueType);
    }
}
