package com.usebilbo.vertx.module.provider;

import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.module.CoreInjector;

public abstract class AbstractOptionsProvider<T> implements Provider<T> {
    private static final Logger LOG = LogManager.getLogger();
    
    private final CoreInjector injector;
    private final Reflections reflections;
    private final Class<? extends Annotation> annotation;
    private final String name;

    @Inject
    public AbstractOptionsProvider(CoreInjector injector, Reflections reflections, Class<? extends Annotation> annotation, String name) {
        this.injector = injector;
        this.reflections = reflections;
        this.annotation = annotation;
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T get() {
        T options = newOptions();
        
        reflections.getTypesAnnotatedWith(annotation).stream()
                .peek(cls -> LOG.info("Configuring {} with {}", name, cls.getSimpleName()))
                .sorted(ProviderHelper.ORDER_COMPARATOR)
                .map(cls -> (Configurator<T>) injector.get(cls))
                .forEach(configurator -> configurator.configure(options));
        
        return options;
    }
    
    abstract protected T newOptions();
}
