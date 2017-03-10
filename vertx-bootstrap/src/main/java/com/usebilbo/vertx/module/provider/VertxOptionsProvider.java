package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.VertxOptionConfiguration;

import io.vertx.core.VertxOptions;

@Singleton
public class VertxOptionsProvider extends AbstractOptionsProvider<VertxOptions> {
    @Inject
    public VertxOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, VertxOptionConfiguration.class, "Vertx");
    }

    @Override
    protected VertxOptions newOptions() {
        return new VertxOptions();
    }
}
