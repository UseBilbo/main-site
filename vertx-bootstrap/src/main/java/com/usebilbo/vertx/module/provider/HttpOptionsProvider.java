package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.module.CoreInjector;

import io.vertx.core.http.HttpServerOptions;

@Singleton
public class HttpOptionsProvider extends AbstractOptionsProvider<HttpServerOptions> {
    @Inject
    public HttpOptionsProvider(CoreInjector injector, Reflections reflections) {
        super(injector, reflections, HttpOptionConfiguration.class, "HTTP Server");
    }

    @Override
    protected HttpServerOptions newOptions() {
        return new HttpServerOptions();
    }
}
