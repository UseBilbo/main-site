package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.module.CoreInjector;

import io.vertx.core.VertxOptions;

@Singleton
public class VertxOptionsProvider extends AbstractOptionsProvider<VertxOptions> {
    @Inject
    public VertxOptionsProvider(CoreInjector injector, Reflections reflections) {
        super(injector, reflections, VertxOptionConfiguration.class, "Vertx");
    }

    @Override
    protected VertxOptions newOptions() {
        return new VertxOptions();
    }
}
