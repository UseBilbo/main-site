package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.RestRoute;
import com.usebilbo.vertx.cluster.api.BeanParser;
import com.usebilbo.vertx.web.configuration.RestBean;

@Singleton
public class RestBeansProvider extends AbstractBeanProvider<RestBean> {

    @Inject
    public RestBeansProvider(BeanParser<RestBean> parser, Reflections reflections) {
        super(parser, reflections, RestRoute.class);
    }
}
