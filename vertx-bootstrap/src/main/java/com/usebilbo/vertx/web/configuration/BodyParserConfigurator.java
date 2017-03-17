package com.usebilbo.vertx.web.configuration;

import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.Order;
import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.annotation.RoutingOrder;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

@Singleton
@Order(RoutingOrder.BODY_PARSER)
@RouterConfiguration
public class BodyParserConfigurator implements Configurator<Router> {
    @Override
    public void configure(Router router) {
        router.route().handler(BodyHandler.create());
    }
}
