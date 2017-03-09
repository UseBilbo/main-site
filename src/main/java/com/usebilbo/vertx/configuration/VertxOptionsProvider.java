package com.usebilbo.vertx.configuration;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.VertxOptionConfiguration;

import io.vertx.core.VertxOptions;

@Singleton
public class VertxOptionsProvider implements Provider<VertxOptions> {
    private static final Logger LOG = LogManager.getLogger();
    
    private final Injector injector;
    private final Reflections reflections;

    @Inject
    public VertxOptionsProvider(Injector injector, Reflections reflections) {
        this.injector = injector;
        this.reflections = reflections;
    }

    @SuppressWarnings("unchecked")
    @Override
    public VertxOptions get() {
        VertxOptions options = new VertxOptions();
        
        reflections.getTypesAnnotatedWith(VertxOptionConfiguration.class).stream()
                .filter(cls -> {LOG.info("Configuring Vertx with {}", cls.getSimpleName()); return true;})
                .map(cls -> (Configurator<VertxOptions>) injector.getInstance(cls))
                .forEach(configurator -> configurator.configure(options));
        
        return options;
    }

}
