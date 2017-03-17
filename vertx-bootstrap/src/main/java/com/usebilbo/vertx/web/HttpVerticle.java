package com.usebilbo.vertx.web;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.annotation.AppVerticle;
import com.usebilbo.vertx.annotation.Options;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

@AppVerticle
@Options(ha=true)
public class HttpVerticle extends AbstractVerticle {
    private static final Logger LOG = LogManager.getLogger();
    
    private final HttpServerOptions options;
    private final Provider<Router> routerProvider;
    
    @Inject
    public HttpVerticle(HttpServerOptions options, Provider<Router> routerProvider) {
        this.options = options;
        this.routerProvider = routerProvider;
    }

    @Override
    public void start() {
        Router router = routerProvider.get();
        vertx.createHttpServer(options).requestHandler(router::accept).listen(options.getPort());

        LOG.info("HTTP server is listening at " + options.getPort());
    }
}
