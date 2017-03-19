package com.usebilbo.vertx.cluster.api;

import com.usebilbo.vertx.util.FieldAdapter;

public interface PersistentConfig {

    String cacheName();

    boolean transactional();

    FieldAdapter key();

    Class<?> keyType();

    Class<?> valueType();
    
    Class<?> daoInterface();
    
    Class<?> daoClass();
}