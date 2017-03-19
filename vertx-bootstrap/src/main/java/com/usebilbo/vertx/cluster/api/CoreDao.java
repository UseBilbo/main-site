package com.usebilbo.vertx.cluster.api;

import java.util.Optional;

import javax.cache.Cache;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlQuery;

import com.usebilbo.vertx.exception.CorePersistenceException;

public interface CoreDao<K, V> {
    /**
     * Get access to underlying cache.
     */
    IgniteCache<K, V> cache();

    /**
     * Create new query.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    SqlQuery<K, V> newQuery(String sql, Object... args);

    /**
     * 
     */
    PersistentConfig config();

    /**
     * Add value or update existing with same key.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    Optional<V> persist(V value);

    /**
     * Remove value either by key or by value. One of these parameters can be
     * <code>null</code>.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    Optional<V> remove(K key, V value);

    /**
     * Get value associated with key.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    default Optional<V> get(K key) {
            return getOrNew(key, null);
    }
    
    /**
     * Get value associated with key or create one if none exists. Pass
     * <code>null</code> as the <code>factory</code> if creation of new value is
     * not allowed.
     * 
     * @throws CorePersistenceException
     *             in case of error.
     */
    Optional<V> getOrNew(K key, OnNew<K, V> factory);

    /**
     * Start execution of the query.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    <R> QueryCursor<R> query(Query<R> query);

    /**
     * Start execution of the query.
     *
     * @throws CorePersistenceException
     *             in case of error.
     */
    QueryCursor<Cache.Entry<K, V>> query(String sql, Object ...args);
    
    /**
     * Create new instance. Note that passed key value can be null. In this case
     * key field should remain <code>null</code> in the created instance.
     */
    public static interface OnNew<K, V> {
            V create(K key);
    }
}
