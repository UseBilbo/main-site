package com.usebilbo.vertx.cluster.impl;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMemoryMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.igfs.IgfsGroupDataBlocksKeyMapper;

import com.usebilbo.vertx.cluster.CacheConfigurationFactory;
import com.usebilbo.vertx.cluster.CacheStoreFactoryProvider;
import com.usebilbo.vertx.properties.PropertyContainer;

@Singleton
public class DefaultCacheConfigurationFactory implements CacheConfigurationFactory {
    private final CacheStoreFactoryProvider storeProvider;
    private final int igfsGroupSize;
    private final int persistentBackups;
    private final int transientBackups;
    private final long offHeapSize;

    @Inject
    //TODO: rename properties
    public DefaultCacheConfigurationFactory(CacheStoreFactoryProvider storeProvider,
                                            @Named("grid.offheap.memory.per.cache") PropertyContainer offHeapSize,
                                            @Named("grid.transient.backups") PropertyContainer transientBackups,
                                            @Named("grid.persistent.backups") PropertyContainer persistentBackups,
                                            @Named("grid.igfs.group.size") PropertyContainer igfsGroupSize) {
        this.storeProvider = storeProvider;
        this.igfsGroupSize = igfsGroupSize.asInt(256);
        this.persistentBackups = persistentBackups.asInt();
        this.transientBackups = transientBackups.asInt();
        this.offHeapSize = offHeapSize.asSpace();
    }

    @Override
    public<K,V> CacheConfiguration<K, V> transientAtomicDistributedCache(String cacheName) {
        CacheConfiguration<K, V> configuration = new CacheConfiguration<>();
        configuration
            .setName(cacheName)
            .setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED)
            .setCacheMode(CacheMode.PARTITIONED)
            .setBackups(transientBackups)
            .setAtomicityMode(CacheAtomicityMode.ATOMIC)
            .setOffHeapMaxMemory(offHeapSize);
        return configuration;
    }
    
    @Override
    public<K,V> CacheConfiguration<K, V> transientAtomicReplicatedCache(String cacheName) {
        CacheConfiguration<K, V> configuration = transientAtomicDistributedCache(cacheName);
        configuration.setCacheMode(CacheMode.REPLICATED);
        return configuration;
    }
    
    @Override
    public CacheConfiguration<?, ?> igfsCache(String cacheName) {
        CacheConfiguration<?, ?> cacheCfg = persistentTransactionalDistributedCache(cacheName, null, null);
        cacheCfg.setAffinityMapper(new IgfsGroupDataBlocksKeyMapper(igfsGroupSize));
        cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        return cacheCfg;
    }
    
    @Override
    public <K,V> CacheConfiguration<K, V> persistentAtomicDistributedCache(String cacheName, Class<K> keyType, Class<V> valueType) {
        CacheConfiguration<K, V> configuration = persistentTransactionalDistributedCache(cacheName, keyType, valueType);
        configuration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        return configuration;
    }
    
    @Override
    public <K,V> CacheConfiguration<K, V> persistentTransactionalDistributedCache(String cacheName, Class<K> keyType, Class<V> valueType) {
        CacheConfiguration<K, V> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setName(cacheName);
        cacheCfg.setCacheStoreFactory(storeProvider.get(cacheName, keyType, valueType));
        
        cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheCfg.setReadThrough(true);
        cacheCfg.setWriteThrough(true);
        cacheCfg.setWriteBehindEnabled(false);
        cacheCfg.setReadFromBackup(true);
        cacheCfg.setLoadPreviousValue(true);
        cacheCfg.setCacheMode(CacheMode.PARTITIONED);
        cacheCfg.setBackups(persistentBackups);
        cacheCfg.setMemoryMode(CacheMemoryMode.OFFHEAP_TIERED);
        cacheCfg.setOffHeapMaxMemory(offHeapSize);
        if (keyType != null && valueType != null) {
            cacheCfg.setTypes(keyType, valueType);
            cacheCfg.setIndexedTypes(keyType, valueType);
        }
        return cacheCfg;
    }
}
