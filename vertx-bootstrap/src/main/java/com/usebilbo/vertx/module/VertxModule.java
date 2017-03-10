package com.usebilbo.vertx.module;

import javax.inject.Inject;

import com.google.common.base.Preconditions;
import com.google.inject.AbstractModule;
import com.usebilbo.vertx.annotation.AppModule;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.file.FileSystem;
import io.vertx.core.shareddata.SharedData;

/**
 * Guice {@link AbstractModule} for vertx and container injections. <br>
 * Borrowed from {@link https://github.com/intappx/vertx-guice/} with minor
 * modifications.
 */
@AppModule
public class VertxModule extends AbstractModule {

    private final Vertx vertx;

    @Inject
    public VertxModule(Vertx vertx) {
        this.vertx = Preconditions.checkNotNull(vertx);
    }

    @Override
    protected void configure() {
        bind(Vertx.class).toInstance(this.vertx);
        bind(EventBus.class).toInstance(this.vertx.eventBus());
        bind(FileSystem.class).toInstance(this.vertx.fileSystem());
        bind(SharedData.class).toInstance(this.vertx.sharedData());
    }
}