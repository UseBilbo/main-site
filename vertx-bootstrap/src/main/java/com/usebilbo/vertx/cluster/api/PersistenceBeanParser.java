package com.usebilbo.vertx.cluster.api;

public interface PersistenceBeanParser {
    PersistenceConfig parse(Class<?> bean);
}
