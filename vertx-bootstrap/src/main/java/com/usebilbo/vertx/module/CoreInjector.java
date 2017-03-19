package com.usebilbo.vertx.module;

import com.google.inject.Module;

public interface CoreInjector {
    <T> T get(Class<T> clazz);

    void install(Module module);

    void install(Iterable<? extends Module> modules);
}
