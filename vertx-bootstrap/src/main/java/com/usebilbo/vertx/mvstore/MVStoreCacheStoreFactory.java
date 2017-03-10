package com.usebilbo.vertx.mvstore;

import javax.cache.configuration.Factory;

import org.apache.ignite.cache.store.CacheStore;
import org.h2.mvstore.MVStore;

public class MVStoreCacheStoreFactory<K, V> implements Factory<CacheStore<? super K, ? super V>> {
    private static final long serialVersionUID = -5252014147741322402L;
    private Class<K> keyType;
    private Class<V> valueType;
    private transient MVStore store;
    private String name;

    public MVStoreCacheStoreFactory(MVStore store, String name, Class<K> keyType, Class<V> valueType) {
        this.store = store;
        this.name = name;
        this.keyType = keyType;
        this.valueType = valueType;
    }
    
    @Override
    public CacheStore<K, V> create() {
        return new MVStoreCacheStore<>(name, store, keyType, valueType);
    }
}
