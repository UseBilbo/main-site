package com.usebilbo.vertx.module.provider;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CachePeekMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.cluster.api.ClusterFileSystem;
import com.usebilbo.vertx.cluster.api.impl.CoreDaoImpl;

@Singleton
public class IgniteProvider extends AbstractSingletonProvider<Ignite> {
    private static final Logger LOG = LogManager.getLogger();
    
    private final IgniteConfiguration configuration;
    
    @Inject
    public IgniteProvider(IgniteConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected Ignite create() {
        return preheatCaches(Ignition.start(configuration));
    }
    
    private Ignite preheatCaches(Ignite ignite) {
        Set<String> fsCacheNames = collectFsCaches(ignite);
        
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        
        pool.submit(() -> Arrays.asList(ignite.configuration().getCacheConfiguration()).stream()
                .filter(conf -> conf.getCacheStoreFactory() != null)
                .filter(conf -> !fsCacheNames.contains(conf.getName()))
                .parallel()
                .forEach(conf -> preheatSingle(conf, ignite)));
        
        LOG.info("Ignite started.");
        
        return ignite;
    }

    private void preheatSingle(CacheConfiguration<?,?> conf, Ignite ignite) {
        ignite.cache(conf.getName()).localLoadCache(null, null, null);
        
        if (!conf.getName().startsWith(ClusterFileSystem.NAME_PREFIX)) {
            long initialValue = ignite.cache(conf.getName()).sizeLong(CachePeekMode.PRIMARY);
            ignite.atomicSequence(conf.getName() + CoreDaoImpl.SUFFIX, initialValue, true);
        }
    }
    
    private Set<String> collectFsCaches(Ignite ignite) {
        Set<String> result = new HashSet<>();
        
        Arrays.asList(ignite.configuration().getFileSystemConfiguration())
            .forEach(fsCfg -> { result.add(fsCfg.getDataCacheName()); result.add(fsCfg.getMetaCacheName()); });
        
        return result;
    }
}
