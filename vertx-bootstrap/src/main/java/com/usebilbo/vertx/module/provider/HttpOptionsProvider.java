package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.HttpOptionConfiguration;

import io.vertx.core.http.HttpServerOptions;

@Singleton
public class HttpOptionsProvider extends AbstractOptionsProvider<HttpServerOptions> {
    @Inject
    public HttpOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, HttpOptionConfiguration.class, "HTTP Server");
    }

    @Override
    protected HttpServerOptions newOptions() {
        return new HttpServerOptions();
    }
}
