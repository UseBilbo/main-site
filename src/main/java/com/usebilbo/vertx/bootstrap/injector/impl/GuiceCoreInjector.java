package com.usebilbo.vertx.bootstrap.injector.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.usebilbo.vertx.bootstrap.injector.CoreInjector;

@Singleton
public class GuiceCoreInjector implements CoreInjector {
    private Injector injector;

    @Inject
    public GuiceCoreInjector(Injector injector) {
        this.injector = injector;
    }

    @Override
    public <T> T get(Class<T> clazz) {
        return injector.getInstance(clazz);
    }

    @Override
    public void install(List<Module> modules) {
        injector = injector.createChildInjector(modules);
    }

    @Override
    public void install(Module module) {
        injector = injector.createChildInjector(module);
    }
}
