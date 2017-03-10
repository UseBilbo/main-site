package com.usebilbo.vertx.cluster.api;

import com.usebilbo.vertx.util.FieldAdapter;

public interface PersistenceConfig {

    String cacheName();

    boolean transactional();

    FieldAdapter key();

    Class<?> keyType();

    Class<?> valueType();
}