package com.usebilbo.vertx.module.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.cluster.CacheConfigurationFactory;
import com.usebilbo.vertx.cluster.api.ClusterFileSystem;
import com.usebilbo.vertx.cluster.api.PersistentConfig;

@Singleton
public class CacheConfigurationProvider extends AbstractSingletonProvider<List<CacheConfiguration<?, ?>>> {
    private static final Logger LOG = LogManager.getLogger();
    
    private final CacheConfigurationFactory configFactory;
    private final List<PersistentConfig> persistentCaches;
  
    @Inject
    public CacheConfigurationProvider(List<PersistentConfig> persistentCaches, CacheConfigurationFactory configFactory) {
        this.persistentCaches = persistentCaches;
        this.configFactory = configFactory;
    }

    @Override
    protected List<CacheConfiguration<?, ?>> create() {
        List<CacheConfiguration<?, ?>> result = new ArrayList<>();
        
        result.addAll(igfsCacheNames().stream().map(name -> configFactory.igfsCache(name)).collect(Collectors.toList()));
        result.addAll(persistentCaches());

        return result;
    }
    
    private Collection<? extends CacheConfiguration<?, ?>> persistentCaches() {
        return persistentCaches.stream()
                .peek((p) -> print(p))
                .map(p -> configFactory.persistentTransactionalDistributedCache(p))
                .collect(Collectors.toList());
    }

    private void print(PersistentConfig p) {
        LOG.info("Persistent {}<{},{}>, cache name: {}", p.valueType().getSimpleName(), p.valueType().getSimpleName(),
                p.keyType().getSimpleName(), p.cacheName());
    }

    private static List<String> igfsCacheNames() {
        List<String> names = new ArrayList<>();
        
        for (ClusterFileSystem value : ClusterFileSystem.values()) {
            names.add(value.dataName());
            names.add(value.metaName());
        }
        return names;
    }
}

