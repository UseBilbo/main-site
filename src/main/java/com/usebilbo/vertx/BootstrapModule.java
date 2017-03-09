package com.usebilbo.vertx;

import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;
import com.usebilbo.vertx.configuration.VertxOptionsProvider;
import com.usebilbo.vertx.properties.AppProperties;
import com.usebilbo.vertx.properties.AppPropertiesImpl;

import io.vertx.core.VertxOptions;

public class BootstrapModule extends AbstractModule {
    private final Reflections reflections;
    private final CommandLine commandLine;

    public BootstrapModule(Reflections reflections, CommandLine commandLine) {
        this.reflections = reflections;
        this.commandLine = commandLine;
    }

    @Override
    protected void configure() {
        bind(Reflections.class).toInstance(reflections);
        bind(CommandLine.class).toInstance(commandLine);
        bind(AppProperties.class).to(AppPropertiesImpl.class);
        OptionalBinder.newOptionalBinder(binder(), VertxOptions.class).setDefault().toProvider(VertxOptionsProvider.class);
    }
}
