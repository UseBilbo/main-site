package com.usebilbo.ignite.persistence.mvstore;

import java.util.HashMap;
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

import com.usebilbo.bootstrap.property.PropertyContainer;
import com.usebilbo.ignite.cache.CacheStoreFactoryProvider;
import com.usebilbo.util.ListUtils;

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
    public MVStoreCacheStoreFactoryProvider(@Named("grid.storage.path") PropertyContainer storage,
                                            @Named("grid.storage.compaction.interval") PropertyContainer interval,
                                            @Named("grid.storage.compaction.fill.rate") PropertyContainer fillRate,
                                            @Named("grid.storage.compaction.write.size") PropertyContainer writeSize) {
        this.fillRate = fillRate.asInt(DEFAULT_FILL_RATE);
        this.writeSize = writeSize.asInt(DEFAULT_WRITE_SIZE);
        this.compactionInterval = interval.asDuration();

        String storageFile = ListUtils.last(storage.list());
        LOG.info("Opening persistent storage at {}", storageFile);
        
        HashMap<String, Object> config = new HashMap<>();
        config.put("compress", true);
        config.put("fileName", storageFile);
        config.put("autoCommitDelay", 100);
        
        store = MVStore.open(storageFile);
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
