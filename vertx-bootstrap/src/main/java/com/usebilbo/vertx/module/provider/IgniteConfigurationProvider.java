package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.module.CoreInjector;

@Singleton
public class IgniteConfigurationProvider  extends AbstractOptionsProvider<IgniteConfiguration> {
    
    @Inject
    public IgniteConfigurationProvider(CoreInjector injector, Reflections reflections) {
        super(injector, reflections, ClusterConfigurator.class, "Ignite");
    }

    @Override
    protected IgniteConfiguration newOptions() {
        return new IgniteConfiguration();
    }
}
