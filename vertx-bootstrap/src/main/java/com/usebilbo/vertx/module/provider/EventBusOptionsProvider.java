package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.EventBusOptionsConfigurator;

import io.vertx.core.eventbus.EventBusOptions;

@Singleton
public class EventBusOptionsProvider extends AbstractOptionsProvider<EventBusOptions> {
    @Inject
    public EventBusOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, EventBusOptionsConfigurator.class, "EventBusOptions");
    }

    @Override
    protected EventBusOptions newOptions() {
        return new EventBusOptions();
    }

}
