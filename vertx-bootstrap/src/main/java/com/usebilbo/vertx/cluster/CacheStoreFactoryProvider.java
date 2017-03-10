package com.usebilbo.vertx.cluster;

import javax.cache.configuration.Factory;

import org.apache.ignite.cache.store.CacheStore;

public interface CacheStoreFactoryProvider {
    <K, V> Factory<? extends CacheStore<? super K, ? super V>> get(String name, Class<K> keyType, Class<V> valueType);
}
