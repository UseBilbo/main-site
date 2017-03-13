package com.usebilbo.vertx.module;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.BootModule;
import com.usebilbo.vertx.annotation.SystemProperties;
import com.usebilbo.vertx.configuration.SystemPropertiesConfigurator;

//TODO: reuse regular Configurator
@BootModule
@Singleton
public class SystemPropertiesModule extends AbstractModule {
    private static final Logger LOG = LogManager.getLogger();
    
    private final Reflections reflections;
    private final Injector injector;

    @Inject
    public SystemPropertiesModule(Reflections reflections, Injector injector) {
        this.reflections = reflections;
        this.injector = injector;
    }

    @Override
    protected void configure() {
        reflections.getTypesAnnotatedWith(SystemProperties.class).stream()
                .filter(cls -> SystemPropertiesConfigurator.class.isAssignableFrom(cls))
                .filter(cls -> { LOG.info("Configuring system properties with {}", cls.getSimpleName()); return true;})
                .map(cls -> injector.getInstance(cls)).map(cfg -> (SystemPropertiesConfigurator) cfg)
                .forEach(cfg -> cfg.configure());
    }

}
