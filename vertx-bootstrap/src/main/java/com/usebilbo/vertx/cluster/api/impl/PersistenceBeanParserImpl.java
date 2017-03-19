package com.usebilbo.vertx.cluster.api.impl;

import static com.usebilbo.vertx.util.Utils.*;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.Persistent;
import com.usebilbo.vertx.cluster.api.BeanParser;
import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.exception.CorePersistenceException;
import com.usebilbo.vertx.properties.PropertyContainer;
import com.usebilbo.vertx.util.Naming;

@Singleton
public class PersistenceBeanParserImpl implements BeanParser<PersistentConfig> {
    private final String schemaPrefix;

    @Inject
    public PersistenceBeanParserImpl(@Named("vertx.persistence.schema.prefix") PropertyContainer schemaPrefix) {
        this.schemaPrefix = ifEmpty(schemaPrefix.asString(""), () -> "", (p) -> p + ".");
        
    }

    @Override
    public PersistentConfig parse(Class<?> bean) {
        Persistent ann = bean.getAnnotation(Persistent.class);
        if (ann == null) {
            throw new CorePersistenceException("No Persistent annotation is present for " + bean.getSimpleName());
        }
        
        return new PersistenceConfigImpl(buildName(ann, bean), ann, bean);
    }

    private String buildName(Persistent ann, Class<?> bean) {
        return schemaPrefix + Naming.name(bean);
    }
}
