package com.usebilbo.vertx.cluster.configuration.mvstore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.cache.Cache.Entry;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

import org.apache.ignite.cache.store.CacheStore;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.mvstore.MVMap;
import org.h2.mvstore.MVMap.Builder;
import org.h2.mvstore.MVStore;
import org.h2.mvstore.type.ObjectDataType;
import org.h2.mvstore.type.StringDataType;

import com.usebilbo.vertx.util.Locker;

public class MVStoreCacheStore<K, V> implements CacheStore<K, V> {
    private static final Logger LOG = LogManager.getLogger();
    
    private final MVMap<K, V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock(false);
    private final String name;

    public MVStoreCacheStore(String name, MVStore store, Class<K> keyType, Class<V> valueType) {
        this.name = name;
        Builder<K, V> builder = new MVMap.Builder<>();
        if (keyType == String.class) {
            builder.keyType(new StringDataType());
        } else {
            builder.keyType(new ObjectDataType());
        }

        this.map = store.openMap(name, builder);
    }

    @Override
    public V load(K key) throws CacheLoaderException {
        try (Locker locker = Locker.forRead(lock)) {
            return map.get(key);
        }
    }

    @Override
    public Map<K, V> loadAll(Iterable<? extends K> keys) throws CacheLoaderException {
        Map<K, V> result = new HashMap<>();
        try (Locker locker = Locker.forRead(lock)) {
            keys.forEach(k -> result.put(k, map.get(k)));
        }
        return result;
    }

    @Override
    public void write(Entry<? extends K, ? extends V> entry) throws CacheWriterException {
        try (Locker locker = Locker.forWrite(lock)) {
            map.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void writeAll(Collection<Entry<? extends K, ? extends V>> entries) throws CacheWriterException {
        try (Locker locker = Locker.forWrite(lock)) {
            entries.forEach(e -> map.put(e.getKey(), e.getValue()));
        }
    }

    @Override
    public void delete(Object key) throws CacheWriterException {
        try (Locker locker = Locker.forWrite(lock)) {
            map.remove(key);
        }
    }

    @Override
    public void deleteAll(Collection<?> keys) throws CacheWriterException {
        try (Locker locker = Locker.forWrite(lock)) {
            keys.stream().forEach(k -> map.remove(k));
        }
    }

    @Override
    public void loadCache(IgniteBiInClosure<K, V> clo, Object... args) throws CacheLoaderException {
        map.forEach((k, v) -> clo.apply(k, v));
        LOG.info("Cache {} is loaded", name);
    }

    @Override
    public void sessionEnd(boolean commit) throws CacheWriterException {
    }
}
