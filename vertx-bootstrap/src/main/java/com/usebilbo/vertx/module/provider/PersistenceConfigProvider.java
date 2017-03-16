package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.Persistent;
import com.usebilbo.vertx.cluster.api.BeanParser;
import com.usebilbo.vertx.cluster.api.PersistentConfig;

@Singleton
public class PersistenceConfigProvider extends AbstractBeanProvider<PersistentConfig> {
    @Inject
    public PersistenceConfigProvider(BeanParser<PersistentConfig> parser, Reflections reflections) {
        super(parser, reflections, Persistent.class);
    }
}
