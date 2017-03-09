package com.usebilbo.ignite.api.impl;

import java.lang.reflect.Field;

import com.usebilbo.ignite.api.PersistenceConfig;
import com.usebilbo.util.FieldAdapter;

class PersistenceConfigImpl implements PersistenceConfig {
    private final String cacheName;
    private final boolean transactional;
    private final Class<?> valueType;
    private final FieldAdapter keyField;
    
    PersistenceConfigImpl(String cacheName, Field keyField, Class<?> valueType, boolean transactional) {
        this.valueType = valueType;
        this.cacheName = cacheName;
        this.transactional = transactional;
        this.keyField = FieldAdapter.of(keyField);
    }

    @Override
    public String cacheName() {
        return cacheName;
    }

    @Override
    public boolean transactional() {
        return transactional;
    }
    
    @Override
    public FieldAdapter key() {
        return keyField;
    }

    @Override
    public Class<?> keyType() {
        return keyField.type();
    }

    @Override
    public Class<?> valueType() {
        return valueType;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append("[ name: ").append(cacheName).append(", ")
                .append( transactional ? "transactional" : "atomic")
                .append(", <").append(keyType().getSimpleName())
                .append(", ").append(valueType().getSimpleName())
                .append(">");

        builder.append("]").toString();
        
        return builder.toString(); 
    }
}
