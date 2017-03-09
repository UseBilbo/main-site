package com.usebilbo.ignite.api;

import com.usebilbo.util.FieldAdapter;

public interface PersistenceConfig {

    String cacheName();

    boolean transactional();

    FieldAdapter key();

    Class<?> keyType();

    Class<?> valueType();
}