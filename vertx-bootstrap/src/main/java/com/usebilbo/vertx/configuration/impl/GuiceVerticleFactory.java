package com.usebilbo.vertx.configuration.impl;
import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.usebilbo.vertx.GuiceVertxLauncher;

import io.vertx.core.Verticle;
import io.vertx.core.impl.verticle.CompilingClassLoader;
import io.vertx.core.spi.VerticleFactory;

/**
 * Represents verticle factory which uses Guice for verticle creation.
 * To make vertx to use this factory for verticle creation the following criteria should be accomplished:
 * 1) This factory should be registered in Vertx. One of the way to achieve this is to use {@link GuiceVertxLauncher}
 * as Vertx main launcher.
 * 2) Verticle should be deployed with the factory prefix {@link #PREFIX}.
 * <br>
 * Borrowed from {@link https://github.com/intappx/vertx-guice/} with minor modifications.  
 */
public class GuiceVerticleFactory implements VerticleFactory {
    public static final String PREFIX = "java-guice";
    private final Injector injector;

    public GuiceVerticleFactory(Injector injector) {
        this.injector = Preconditions.checkNotNull(injector);
    }

    @Override
    public String prefix() {
        return PREFIX;
    }

    @Override
    public Verticle createVerticle(String verticleName, ClassLoader classLoader) throws Exception {
        verticleName = VerticleFactory.removePrefix(verticleName);

        Class<?> clazz;
        if (verticleName.endsWith(".java")) {
            CompilingClassLoader compilingLoader = new CompilingClassLoader(classLoader, verticleName);
            String className = compilingLoader.resolveMainClassName();
            clazz = compilingLoader.loadClass(className);
        } else {
            clazz = classLoader.loadClass(verticleName);
        }

        return (Verticle) this.injector.getInstance(clazz);
    }

    public Injector getInjector() {
        return injector;
    }
}