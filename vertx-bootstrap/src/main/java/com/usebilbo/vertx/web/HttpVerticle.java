package com.usebilbo.vertx.web;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.annotation.AppVerticle;
import com.usebilbo.vertx.module.provider.RouterProvider;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;

@AppVerticle
public class HttpVerticle extends AbstractVerticle {
    private static final Logger LOG = LogManager.getLogger();
    
    private final HttpServerOptions options;
    private final RouterProvider routerProvider;
    
    @Inject
    public HttpVerticle(HttpServerOptions options, RouterProvider routerProvider) {
        this.options = options;
        this.routerProvider = routerProvider;
    }

    @Override
    public void start() {
        Router router = routerProvider.get();
        vertx.createHttpServer(options).requestHandler(router::accept).listen(options.getPort());
        //TODO: move it to dedicated configurator
//
//        Router router = Router.router(vertx);
//
//        // Serve the static pages
//        StaticHandler staticHandler = StaticHandler.create()
//                .setCachingEnabled(false)
//                .setFilesReadOnly(false)
//                .setEnableRangeSupport(true)
//                .setAlwaysAsyncFS(false)
//                .setEnableFSTuning(false)
//                .setWebRoot("landing-page");
//
//        //router.route("/api/instantRegister/*").handler()
//        router.route().handler(staticHandler);
//
        LOG.info("HTTP server is listening at " + options.getPort());
    }
}
