package com.usebilbo.vertx.module;

import java.time.Clock;

import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.usebilbo.vertx.annotation.UTC;
import com.usebilbo.vertx.configuration.CommandLine;
import com.usebilbo.vertx.properties.GroupBuilder;
import com.usebilbo.vertx.properties.impl.GroupBuilderImpl;
import com.usebilbo.vertx.util.ClockService;
import com.usebilbo.vertx.util.impl.UtcClockService;

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
        bind(GroupBuilder.class).to(GroupBuilderImpl.class);
        bind(Clock.class).annotatedWith(UTC.class).toInstance(Clock.systemUTC());
        bind(ClockService.class).annotatedWith(UTC.class).to(UtcClockService.class);
    }
}
