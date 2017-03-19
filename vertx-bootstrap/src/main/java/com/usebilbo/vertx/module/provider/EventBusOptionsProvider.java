package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.EventBusOptionsConfigurator;
import com.usebilbo.vertx.module.CoreInjector;

import io.vertx.core.eventbus.EventBusOptions;

@Singleton
public class EventBusOptionsProvider extends AbstractOptionsProvider<EventBusOptions> {
    @Inject
    public EventBusOptionsProvider(CoreInjector injector, Reflections reflections) {
        super(injector, reflections, EventBusOptionsConfigurator.class, "EventBusOptions");
    }

    @Override
    protected EventBusOptions newOptions() {
        return new EventBusOptions();
    }

}
