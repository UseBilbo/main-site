package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.ClusterConfigurator;

@Singleton
public class IgniteConfigurationProvider  extends AbstractOptionsProvider<IgniteConfiguration> {
    
    @Inject
    public IgniteConfigurationProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, ClusterConfigurator.class, "Ignite");
    }

    @Override
    protected IgniteConfiguration newOptions() {
        return new IgniteConfiguration();
    }
}
