package com.usebilbo.vertx.module.provider;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.EventBusOptionsConfigurator;

import io.vertx.core.eventbus.EventBusOptions;

public class EventBusOptionsProvider extends AbstractOptionsProvider<EventBusOptions> {

    public EventBusOptionsProvider(Injector injector, Reflections reflections) {
        super(injector, reflections, EventBusOptionsConfigurator.class, "EventBusOptions");
    }

    @Override
    protected EventBusOptions newOptions() {
        return new EventBusOptions();
    }

}
