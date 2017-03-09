package com.usebilbo.ignite.api;

public interface PersistenceBeanParser {
    PersistenceConfig parse(Class<?> bean);
}
