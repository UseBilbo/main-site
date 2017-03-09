package com.usebilbo.ignite.configuration.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

@Singleton
@GridConfigurator
public class GridCacheConfigurator implements IgniteConfigurator {
    private final List<CacheConfiguration<?, ?>> configs;

    @Inject
    public GridCacheConfigurator(List<CacheConfiguration<?, ?>> configs) {
        this.configs = configs;
    }

    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setCacheConfiguration(configs.toArray(new CacheConfiguration[configs.size()]));
    }
}
