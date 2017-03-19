package com.usebilbo.vertx.cluster;

import javax.cache.configuration.Factory;

import org.apache.ignite.cache.store.CacheStore;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.cluster.configuration.mvstore.MVStoreCacheStoreFactoryProvider;

@ImplementedBy(MVStoreCacheStoreFactoryProvider.class)
public interface CacheStoreFactoryProvider {
    <K, V> Factory<? extends CacheStore<? super K, ? super V>> get(String name, Class<K> keyType, Class<V> valueType);
}
