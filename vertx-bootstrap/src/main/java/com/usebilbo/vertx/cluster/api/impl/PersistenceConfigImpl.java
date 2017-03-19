package com.usebilbo.vertx.cluster.api.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import com.usebilbo.vertx.annotation.ID;
import com.usebilbo.vertx.annotation.Persistent;
import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.exception.CorePersistenceException;
import com.usebilbo.vertx.util.ClassUtils;
import com.usebilbo.vertx.util.FieldAdapter;

class PersistenceConfigImpl implements PersistentConfig {
    private final String cacheName;
    private final boolean transactional;
    private final Class<?> valueType;
    private final FieldAdapter keyField;
    private final Class<?> daoInterface;
    private final Class<?> daoImpl;
    
    public PersistenceConfigImpl(String cacheName, Persistent annotation, Class<?> valueType) {
        this.cacheName = cacheName;
        this.valueType = valueType;
        this.keyField = FieldAdapter.of(locateKey(valueType));
        this.transactional = annotation.transactional();
        this.daoInterface = annotation.iface();
        this.daoImpl = annotation.impl();
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

    @Override
    public Class<?> daoInterface() {
        return daoInterface;
    }
    
    @Override
    public Class<?> daoClass() {
        return daoImpl;
    }

    private static Field locateKey(Class<?> bean) {
        List<Field> keys = ClassUtils.fieldsOf(bean).filter(fld -> fld.isAnnotationPresent(ID.class))
                                    .collect(Collectors.toList());

        if (keys.size() != 1) {
            throw new CorePersistenceException("Class " + bean.getSimpleName() + " must contain exactly one field with @ID annotation");
        }

        return keys.get(0);
    }
}
