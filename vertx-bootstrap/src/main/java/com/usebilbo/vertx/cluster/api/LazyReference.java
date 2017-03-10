package com.usebilbo.vertx.cluster.api;

public interface LazyReference<T> {
    T get();
}
