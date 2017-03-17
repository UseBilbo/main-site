package com.usebilbo.vertx.web.configuration;

import static com.usebilbo.vertx.util.Utils.doIfNotNull;

import java.util.List;
import java.util.function.Predicate;

import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Injector;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class AbstractRestConfigurator implements Configurator<Router> {
    private static final Logger LOG = LogManager.getLogger();
        
    private final List<RestBean> beans;
    private final Provider<Vertx> vertx;
    private final Injector injector;
    private final Predicate<RestBean> filteringPredicate;
    private final String text;

    public AbstractRestConfigurator(Injector injector, Provider<Vertx> vertx, List<RestBean> beans, Predicate<RestBean> filteringPredicate, String text) {
        this.injector = injector;
        this.vertx = vertx;
        this.beans = beans;
        this.filteringPredicate = filteringPredicate;
        this.text = text;
    }

    @Override
    public void configure(Router router) {
        beans.stream().filter(filteringPredicate).forEach(bean -> configure(bean, router));
    }

    private void configure(RestBean bean, Router router) {
        Router subRouter = Router.router(vertx.get());
        Object instance = injector.getInstance(bean.type());
        
        bean.methods().stream().peek(m -> print(m, bean)).forEach(method -> addRoute(subRouter, method, bean, instance));
        router.mountSubRouter(bean.path(), subRouter);
    }

    //TODO: add failure handler?
    private void addRoute(Router subRouter, RestMethod method, RestBean bean, Object instance) {
        Route route = subRouter.route();

        doIfNotNull(method.consumes(), v -> route.consumes(v));
        doIfNotNull(method.produces(), v -> route.produces(v));

        route.method(method.method());
        
        if (method.isRegexp()) {
            route.pathRegex(method.path());
        } else {
            route.path(method.path());
        }
        
        if (method.isBlocking()) {
            route.blockingHandler((context) -> method.call(context, instance));
        } else {
            route.handler((context) -> method.call(context, instance));
        }
    }
    
    private void print(RestMethod method, RestBean bean) {
        LOG.info("({}) REST API: {}{}", text, bean.path(), method.path());
    }
}
