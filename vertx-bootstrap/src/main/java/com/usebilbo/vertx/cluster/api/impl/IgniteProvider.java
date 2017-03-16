package com.usebilbo.vertx.cluster.api.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicMarkableReference;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

@Singleton
public class IgniteProvider implements Provider<Ignite> {
    private final AtomicMarkableReference<Ignite> ignite = new AtomicMarkableReference<>(null, false);
    private final IgniteConfiguration configuration;
    
    @Inject
    public IgniteProvider(IgniteConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Ignite get() {
        if (ignite.isMarked()) {
            return ignite.getReference();
        }
        
        ignite.set(Ignition.start(configuration), true);

        preheatCaches(ignite.getReference());
        
        return ignite.getReference();
    }

    private void preheatCaches(Ignite ignite) {
        Set<String> fsCacheNames = collectFsCaches(ignite);
        
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        
        pool.submit(() -> Arrays.asList(ignite.configuration().getCacheConfiguration()).stream()
            .filter(conf -> conf.getCacheStoreFactory() != null)
            .filter(conf -> !fsCacheNames.contains(conf.getName()))
            .parallel().forEach(conf -> ignite.cache(conf.getName()).localLoadCache(null, null, null)));
    }

    private Set<String> collectFsCaches(Ignite ignite) {
        Set<String> result = new HashSet<>();
        
        Arrays.asList(ignite.configuration().getFileSystemConfiguration())
            .forEach(fsCfg -> { result.add(fsCfg.getDataCacheName()); result.add(fsCfg.getMetaCacheName()); });

        return result;
    }
}
