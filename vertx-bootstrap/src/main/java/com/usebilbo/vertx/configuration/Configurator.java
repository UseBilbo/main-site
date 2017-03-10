package com.usebilbo.vertx.configuration;

public interface Configurator<T> {
    void configure(T options);
}
