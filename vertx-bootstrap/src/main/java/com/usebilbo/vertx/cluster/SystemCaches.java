package com.usebilbo.vertx.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.CacheConfiguration;

import com.usebilbo.vertx.annotation.ClusterCacheConfigurations;
import com.usebilbo.vertx.cluster.api.ClusterFileSystem;

/**
 * Configurator for system caches.
 */
@Singleton
@ClusterCacheConfigurations
//TODO: rework it according to current requirements
public class SystemCaches implements CacheConfigurations {
    private List<String> igfsCaches;
    private final CacheConfigurationFactory configFactory;

    @Inject
    public SystemCaches(CacheConfigurationFactory configFactory) {
        this.configFactory = configFactory;
        this.igfsCaches = fillCacheNames();
    }

    private List<String> fillCacheNames() {
        List<String> names = new ArrayList<>();
        for (ClusterFileSystem value : ClusterFileSystem.values()) {
            names.add(value.dataName());
            names.add(value.metaName());
        }
        return names;
    }

    @Override
    public List<CacheConfiguration<?, ?>> get() {
        return igfsCaches.stream().map(name -> configFactory.igfsCache(name)).collect(Collectors.toList());
    }
}
