package com.usebilbo.vertx.cluster.api.impl;

import static com.usebilbo.vertx.util.Utils.coalesce;

import java.util.Optional;

import javax.cache.Cache;
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
    public static final String SUFFIX = "-sequence";
    
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
        K key = key(value);

        if (key == null) {
            do {
                key = key(value, generateKey());
            } while (!cache().putIfAbsent(key, value));
        } else {
            cache().put(key, value);
        }
        return Optional.of(value);
    }

    private K key(V value) {
        return config.key().orNull(value);
    }

    private K key(V value, K key) {
        try {
            config.key().set(value, key);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new CorePersistenceException(
                    "Exception while setting key to value of type " + valueType.getSimpleName(), e);
        }
        return key;
    }

    private K generateKey() {
        return converter.convert(sequence.get().incrementAndGet());
    }

    @Override
    public Optional<V> remove(K key, V value) {
        K keyToRemove = coalesce(() -> key, () -> key(value));

        if (keyToRemove == null) {
            return Optional.empty();
        }

        cache().remove(keyToRemove);

        return Optional.ofNullable(value);
    }

    @Override
    public Optional<V> getOrNew(K key, OnNew<K, V> factory) {
        if (key == null) {
            if (factory == null) {
                return Optional.empty();
            }
            return persist(factory.create(null));
        }

        return Optional.ofNullable(cache().getAndPutIfAbsent(key, factory.create(key)));
    }

    @Override
    public <R> QueryCursor<R> query(Query<R> query) {
        return cache().query(query);
    }

    @Override
    public QueryCursor<Cache.Entry<K, V>> query(String sql, Object... args) {
        return query(newQuery(sql, args));
    }
}
