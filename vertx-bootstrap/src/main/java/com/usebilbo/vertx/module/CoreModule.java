package com.usebilbo.vertx.module;

import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.usebilbo.vertx.configuration.CommandLine;

/**
 * Initial module used to build root injector.
 */
public class CoreModule extends AbstractModule {
    private final Reflections reflections;
    private final CommandLine commandLine;

    public CoreModule(Reflections reflections, CommandLine commandLine) {
        this.reflections = reflections;
        this.commandLine = commandLine;
    }

    @Override
    protected void configure() {
        bind(Reflections.class).toInstance(reflections);
        bind(CommandLine.class).toInstance(commandLine);
    }
}
