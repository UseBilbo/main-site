package com.usebilbo.vertx.web.configuration;

import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

@Singleton
@RouterConfiguration
public class StaticRouteConfigurator implements Configurator<Router> {

    @Override
    public void configure(Router router) {
        //TODO: load configuration from properties
        // Serve the static pages
        StaticHandler staticHandler = StaticHandler.create()
                .setCachingEnabled(false)
                .setFilesReadOnly(false)
                .setEnableRangeSupport(true)
                .setAlwaysAsyncFS(false)
                .setEnableFSTuning(false)
                .setWebRoot("landing-page");

        router.route().handler(staticHandler);
    }
}
