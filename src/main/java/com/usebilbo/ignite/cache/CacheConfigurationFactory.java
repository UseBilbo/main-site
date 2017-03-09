package com.usebilbo.ignite.cache;

import org.apache.ignite.configuration.CacheConfiguration;

import com.usebilbo.ignite.api.PersistenceConfig;

public interface CacheConfigurationFactory {

    CacheConfiguration<?, ?> igfsCache(String cacheName);

    <K, V> CacheConfiguration<K, V> transientAtomicReplicatedCache(String cacheName);
    <K, V> CacheConfiguration<K, V> transientAtomicDistributedCache(String cacheName);
    <K, V> CacheConfiguration<K, V> persistentTransactionalDistributedCache(String cacheName, Class<K> keyType, Class<V> valueType);
    <K, V> CacheConfiguration<K, V> persistentAtomicDistributedCache(String cacheName, Class<K> keyType, Class<V> valueType);
    
    default CacheConfiguration<?, ?> persistentCache(PersistenceConfig config) {
        return config.transactional() ?
              persistentTransactionalDistributedCache(config.cacheName(), config.keyType(), config.valueType()) :                         
              persistentAtomicDistributedCache(config.cacheName(), config.keyType(), config.valueType());                         
    }
}
