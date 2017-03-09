package com.usebilbo.bootstrap.injector;

import java.util.List;

import com.google.inject.Module;

public interface CoreInjector {
    <T> T get(Class<T> clazz);

    void install(Module module);

    void install(List<Module> modules);
}
