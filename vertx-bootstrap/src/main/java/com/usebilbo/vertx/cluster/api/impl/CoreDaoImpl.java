package com.usebilbo.vertx.cluster.api.impl;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.ignite.IgniteAtomicSequence;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;

import com.google.inject.TypeLiteral;
import com.usebilbo.vertx.cluster.api.CoreDao;
import com.usebilbo.vertx.cluster.api.LazyReference;
import com.usebilbo.vertx.cluster.api.LazyReferenceFactory;
import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.cluster.api.Schema;
import com.usebilbo.vertx.exception.CorePersistenceException;

public class CoreDaoImpl<K, V> implements CoreDao<K, V> {
    private static final String SUFFIX = "-sequence";
    private final Class<K> keyType;
    private final Class<V> valueType;
    private final PersistentConfig config;
    private final LazyReference<IgniteCache<K, V>> cache;
    private final LazyReference<IgniteAtomicSequence> sequence;
    private final SimpleConverter<K> converter;
    
    private static interface SimpleConverter<K> {
        K convert(long source);
    }
    
    @SuppressWarnings("unchecked")
    @Inject
    public CoreDaoImpl(TypeLiteral<K> key, TypeLiteral<V> val, LazyReferenceFactory factory, Schema schema) {
        this.keyType = (Class<K>) key.getRawType();
        this.valueType = (Class<V>) val.getRawType();
        this.config = schema.config(valueType);
        this.cache = factory.cache(config.cacheName());
        this.sequence = factory.sequence(config.cacheName() + SUFFIX);
        
        if (keyType.isAssignableFrom(String.class)) {
            this.converter = (src) -> (K) Long.toHexString(src);
        } else if (keyType.isAssignableFrom(Long.class)) {
            this.converter = (src) -> (K) Long.valueOf(src);
        } else {
            throw new CorePersistenceException("Only String and Long ID's are supported");
        }
    }

    @Override
    public IgniteCache<K, V> cache() {
        return cache.get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public SqlQuery<K, V> newQuery(String sql, Object... args) {
            return (SqlQuery<K, V>) new SqlQuery<>(valueType, sql).setArgs(args);
    }
    
    @Override
    public PersistentConfig config() {
        return config;
    }

    @Override
    public Optional<V> persist(V value) {
        K key = config.key().orNull(value);
        
        if (key == null) {
            do {
                key = generateKey();
                config.key().set(value, key);
                
            } while(cache.get().putIfAbsent(key, value));
            
            return storeNew(value);
        } else {
            cache.get().put(key, value);
            return Optional.of(value);
        }
    }

    private K generateKey() {
        return converter.convert(sequence.get().incrementAndGet());
    }

    private Optional<V> storeNew(V value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<V> remove(K key, V value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<V> getOrNew(K key, com.usebilbo.vertx.cluster.api.CoreDao.OnNew<K, V> factory) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <R> QueryCursor<R> query(Query<R> query) {
        // TODO Auto-generated method stub
        return null;
    }

}
