package com.usebilbo.vertx.cluster.api.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.cluster.api.Schema;

@Singleton
public class SchemaImpl implements Schema {
    private final Map<Class<?>, PersistentConfig> configs;

    @Inject
    public SchemaImpl(List<PersistentConfig> configs) {
        this.configs = configs.stream().collect(Collectors.toMap(cfg -> cfg.valueType(), cfg -> cfg));
    }
    
    @Override
    public <V> PersistentConfig config(Class<V> entityType) {
        return configs.get(entityType);
    }
}
