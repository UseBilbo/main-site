package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.module.CoreInjector;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@Singleton
public class RouterProvider extends AbstractOptionsProvider<Router> {
    private final Vertx vertx;

    @Inject
    public RouterProvider(CoreInjector injector, Reflections reflections, Vertx vertx) {
        super(injector, reflections, RouterConfiguration.class, "Router");
        this.vertx = vertx;
    }

    @Override
    protected Router newOptions() {
        return Router.router(vertx);
    }
}
