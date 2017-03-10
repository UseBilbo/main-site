package com.usebilbo.vertx.cluster;

import java.util.ArrayList;
import java.util.Collection;
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
public class SystemCaches implements CacheConfigurations {
    public static final String NODE_GROUPS_CACHE = "node.groups";
    public static final String NODE_SERVICES_CACHE = "node.node-to-services";
    public static final String SERVICE_NODES_CACHE = "node.service-to-nodes";
    public static final String NODE_VARIABLES_CACHE = "node.variables";

    private static final String[] SYSTEM_CACHES = {NODE_GROUPS_CACHE, NODE_SERVICES_CACHE, SERVICE_NODES_CACHE, NODE_VARIABLES_CACHE};

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
        List<CacheConfiguration<?, ?>> result = new ArrayList<>();
        result.addAll(systemCaches());
        result.addAll(igfsCaches());
        return result;
    }

    private List<CacheConfiguration<?, ?>> systemCaches() {
        List<CacheConfiguration<?, ?>> result = new ArrayList<>();

        for (String name : SYSTEM_CACHES) {
            result.add(configFactory.transientAtomicReplicatedCache(name));
        }

        return result;
    }

    private Collection<? extends CacheConfiguration<?, ?>> igfsCaches() {
        return igfsCaches.stream().map(name -> configFactory.igfsCache(name)).collect(Collectors.toList());
    }
}
