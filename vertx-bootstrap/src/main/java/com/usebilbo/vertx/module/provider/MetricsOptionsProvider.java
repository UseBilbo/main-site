package com.usebilbo.vertx.module.provider;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.MetricsOptionsConfigurator;

import io.vertx.core.metrics.MetricsOptions;

public class MetricsOptionsProvider extends AbstractOptionsProvider<MetricsOptions> {

    public MetricsOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, MetricsOptionsConfigurator.class, "MetricsOptions");
    }

    @Override
    protected MetricsOptions newOptions() {
        return new MetricsOptions();
    }
}
