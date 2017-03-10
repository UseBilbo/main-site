package com.usebilbo.vertx.module;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;
import com.usebilbo.vertx.annotation.BootModule;
import com.usebilbo.vertx.module.provider.HttpOptionsProvider;
import com.usebilbo.vertx.module.provider.VertxOptionsProvider;

import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServerOptions;

@BootModule
@Singleton
public class VertxOptionsModule extends AbstractModule {
    @Override
    protected void configure() {
        OptionalBinder.newOptionalBinder(binder(), VertxOptions.class).setDefault().toProvider(VertxOptionsProvider.class);
        OptionalBinder.newOptionalBinder(binder(), HttpServerOptions.class).setDefault().toProvider(HttpOptionsProvider.class);
    }
}
