package com.usebilbo.vertx.module;

import com.google.inject.AbstractModule;
import com.usebilbo.vertx.annotation.SystemModule;
import com.usebilbo.vertx.module.provider.RouterProvider;

import io.vertx.ext.web.Router;

@SystemModule
public class PostVertxModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Router.class).toProvider(RouterProvider.class);
    }
}
