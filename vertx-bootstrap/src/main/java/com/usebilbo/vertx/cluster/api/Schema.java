package com.usebilbo.vertx.cluster.api;

import com.google.inject.ImplementedBy;
import com.usebilbo.vertx.cluster.api.impl.SchemaImpl;

@ImplementedBy(SchemaImpl.class)
public interface Schema {
    <V> PersistentConfig config(Class<V> entityType);
}
