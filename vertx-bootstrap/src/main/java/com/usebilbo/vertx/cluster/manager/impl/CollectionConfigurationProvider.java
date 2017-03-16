package com.usebilbo.vertx.cluster.manager.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CollectionConfiguration;

import com.usebilbo.vertx.properties.PropertyContainer;

@Singleton
public class CollectionConfigurationProvider implements Provider<CollectionConfiguration> {
    private final CacheAtomicityMode atomicityMode;
    private final CacheMode cacheMode;
    private final CacheMemoryMode memoryMode;
    private final int backups;
    private final long offHeapMaxMem;
    private final boolean collocated;


    @Inject
    public CollectionConfigurationProvider(
            @Named("vertx.manager.queue.atomicity.mode") PropertyContainer atomicityMode,
            @Named("vertx.manager.queue.cache.mode") PropertyContainer cacheMode,
            @Named("vertx.manager.queue.memory.mode") PropertyContainer memoryMode,
            @Named("vertx.manager.queue.backups") PropertyContainer backups,
            @Named("vertx.manager.queue.offHeapMaxMem") PropertyContainer offHeapMaxMem,
            @Named("vertx.manager.queue.collocated") PropertyContainer collocated) {
        this.atomicityMode = atomicityMode.as(CacheAtomicityMode.class, CacheAtomicityMode.ATOMIC);
        this.cacheMode = cacheMode.as(CacheMode.class, CacheMode.PARTITIONED);
        this.memoryMode = memoryMode.as(CacheMemoryMode.class, CacheMemoryMode.ONHEAP_TIERED);
        this.backups = backups.asInt(0);
        this.offHeapMaxMem = offHeapMaxMem.asLong(-1);
        this.collocated = collocated.asBool(false);
    }

    @Override
    public CollectionConfiguration get() {
        CollectionConfiguration configuration = new CollectionConfiguration();
        configuration.setAtomicityMode(atomicityMode);
        configuration.setCacheMode(cacheMode);
        configuration.setMemoryMode(memoryMode);
        configuration.setBackups(backups);
        configuration.setOffHeapMaxMemory(offHeapMaxMem);
        configuration.setCollocated(collocated);
        return configuration;
    }

}
