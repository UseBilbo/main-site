package com.usebilbo.ignite.api.impl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.reflections.util.Utils;

import com.usebilbo.annotation.ID;
import com.usebilbo.annotation.Persistent;
import com.usebilbo.exception.CorePersistenceException;
import com.usebilbo.ignite.api.PersistenceBeanParser;
import com.usebilbo.ignite.api.PersistenceConfig;
import com.usebilbo.util.ClassUtils;
import com.usebilbo.util.Naming;
import com.usebilbo.vertx.bootstrap.property.PropertyContainer;
import com.usebilbo.vertx.bootstrap.scanner.PackageScanner;

@Singleton
public class PersistenceBeanParserImpl implements PersistenceBeanParser {
    private final String schemaPrefix;

    @Inject
    public PersistenceBeanParserImpl(@Named("persistence.schema.prefix") PropertyContainer schemaPrefix, 
            PackageScanner scanner) {
        this.schemaPrefix = preparePrefix(schemaPrefix.asString(""));
    }

    private static String preparePrefix(String prefix) {
        return Utils.isEmpty(prefix) ? "" : prefix + ".";
    }

    @Override
    public PersistenceConfig parse(Class<?> bean) {
        Persistent ann = bean.getAnnotation(Persistent.class);
        if (ann == null) {
            throw new CorePersistenceException("No Persistent annotation is present for " + bean.getSimpleName());
        }
        
        return new PersistenceConfigImpl(buildName(ann, bean), 
                                         locateKey(bean), 
                                         bean, 
                                         ann.transactional());
    }

    private Field locateKey(Class<?> bean) {
        List<Field> keys = ClassUtils.fieldsOf(bean).filter(fld -> fld.isAnnotationPresent(ID.class))
                                    .collect(Collectors.toList());

        if (keys.size() != 1) {
            throw new CorePersistenceException("Class " + bean.getSimpleName() + " must have exactly one field with @ID annotation");
        }

        return keys.get(0);
    }

    private String buildName(Persistent ann, Class<?> bean) {
        if (!Utils.isEmpty(ann.cacheName())) {
            return ann.cacheName();
        }

        return schemaPrefix + Naming.name(bean);
    }
}
