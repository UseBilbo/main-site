package com.usebilbo.vertx.web.configuration;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.Order;
import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.annotation.RoutingOrder;

import io.vertx.core.Vertx;

@Singleton
@Order(RoutingOrder.BEFORE_BODY_PARSED)
@RouterConfiguration
public class BeforeBodyParsedRestConfigurator extends AbstractRestConfigurator {

    @Inject
    public BeforeBodyParsedRestConfigurator(Injector injector, Provider<Vertx> vertx, List<RestBean> beans) {
        super(injector, vertx, beans, (b) -> b.isBeforeBodyParsed(), "unparsed body");
    }
}
