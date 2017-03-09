package com.usebilbo.vertx.bootstrap.processor;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Module;
import com.usebilbo.vertx.LaunchContext;
import com.usebilbo.vertx.bootstrap.scanner.Entry;

public abstract class AbstractBootProcessor<A extends Annotation, E> implements BootProcessor {
    private final LaunchContext context;
    private final Class<A> annotation;
    private final Logger log;

    public AbstractBootProcessor(LaunchContext context, Class<A> annotation) {
        this.context = context;
        this.annotation = annotation;
        this.log = LogManager.getLogger(getClass());
    }
    
    public Logger log() {
    	return log;
    }

    public List<Entry<E>> entries() {
        return context.configuration().entries(annotation);
    }

    public List<E> instances() {
        return entries().stream()
                .map(entry -> context.injector().get(entry.clazz()))
                .filter(instance -> instance != null)
                .collect(Collectors.toList());
    }

    public List<Class<? extends E>> classes() {
        return entries().stream()
                .map(entry -> entry.clazz())
                .filter(clazz -> clazz != null)
                .collect(Collectors.toList());
    }

    public List<Module> list(Module... modules) {
        return Arrays.asList(modules);
    }

    public LaunchContext context() {
        return context;
    }

    public void install(Module moodule) {
        context().injector().install(moodule);
    }
}
