package com.usebilbo.vertx.cluster.configuration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.configuration.Configurator;

@Singleton
@ClusterConfigurator
public class ClusterCacheConfigurator implements Configurator<IgniteConfiguration> {
    private final List<CacheConfiguration<?, ?>> configs;

    @Inject
    public ClusterCacheConfigurator(List<CacheConfiguration<?, ?>> configs) {
        this.configs = configs;
    }

    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setCacheConfiguration(configs.toArray(new CacheConfiguration[configs.size()]));
    }
}
