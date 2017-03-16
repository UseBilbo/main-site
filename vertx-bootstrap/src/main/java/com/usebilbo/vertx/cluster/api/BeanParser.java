package com.usebilbo.vertx.cluster.api;

public interface BeanParser<T> {
    T parse(Class<?> clazz);
}
