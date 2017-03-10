package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.RouterConfiguration;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@Singleton
public class RouterProvider extends AbstractOptionsProvider<Router> {
    private final Vertx vertx;

    @Inject
    public RouterProvider(Injector injector, Reflections reflections, Vertx vertx) {
        super(injector, reflections, RouterConfiguration.class, "Router");
        this.vertx = vertx;
    }

    @Override
    protected Router newOptions() {
        return Router.router(vertx);
    }
}
