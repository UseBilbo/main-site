package com.usebilbo.vertx.module;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;
import com.usebilbo.vertx.annotation.BootModule;
import com.usebilbo.vertx.annotation.UTC;
import com.usebilbo.vertx.util.ClockService;
import com.usebilbo.vertx.util.impl.UtcClockService;

@BootModule
@Singleton
public class MiscBootstrapModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClockService.class).annotatedWith(UTC.class).to(UtcClockService.class);
    }
}
