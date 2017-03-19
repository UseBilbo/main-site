package com.usebilbo.vertx.cluster.api;

import org.apache.ignite.*;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.CollectionConfiguration;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.cluster.api.impl.LazyReferenceFactoryImpl;

@ImplementedBy(LazyReferenceFactoryImpl.class)
public interface LazyReferenceFactory {
    <K, V> LazyReference<IgniteCache<K, V>> cache(String name);
    
    <K,V> LazyReference<IgniteCache<K, V>> cache(CacheConfiguration<K, V> config);

    LazyReference<IgniteAtomicSequence> sequence(String name);

    LazyReference<IgniteAtomicSequence> sequence(String name, long initialValue);

    LazyReference<IgniteFileSystem> fs(ClusterFileSystem fileSystem);
    
    <T> LazyReference<IgniteQueue<T>> queue(String name, int capacity, CollectionConfiguration configuration);
    
    <T> LazyReference<IgniteQueue<T>> queueSmall(String name);

    <T> LazyReference<IgniteQueue<T>> queueLarge(String name);
    
    LazyReference<TransactionManager> tm();
    
    LazyReference<IgniteCompute> compute();

    LazyReference<IgniteCompute> compute(ClusterGroup group);

    LazyReference<IgniteEvents> events();

}
