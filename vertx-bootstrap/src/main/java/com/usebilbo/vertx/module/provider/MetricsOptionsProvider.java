package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.MetricsOptionsConfigurator;
import com.usebilbo.vertx.module.CoreInjector;

import io.vertx.core.metrics.MetricsOptions;

@Singleton
public class MetricsOptionsProvider extends AbstractOptionsProvider<MetricsOptions> {
    @Inject
    public MetricsOptionsProvider(CoreInjector injector, Reflections reflections) {
        super(injector, reflections, MetricsOptionsConfigurator.class, "MetricsOptions");
    }

    @Override
    protected MetricsOptions newOptions() {
        return new MetricsOptions();
    }
}
