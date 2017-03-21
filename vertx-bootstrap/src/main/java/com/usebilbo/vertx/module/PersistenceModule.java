package com.usebilbo.vertx.module;

import java.util.List;

import javax.inject.Inject;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.usebilbo.vertx.annotation.SystemModule;
import com.usebilbo.vertx.cluster.api.PersistentConfig;
import com.usebilbo.vertx.module.provider.RouterProvider;
import com.usebilbo.vertx.util.TypeUtils;

import io.vertx.ext.web.Router;

@SystemModule
public class PersistenceModule extends AbstractModule {
    private final List<PersistentConfig> persistentConfigs;

    @Inject
    public PersistenceModule(List<PersistentConfig> persistentConfigs) {
        this.persistentConfigs = persistentConfigs;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected void configure() {
        persistentConfigs.forEach((cfg) -> bind((TypeLiteral) from(cfg.daoInterface(), cfg)).to(from(cfg.daoClass(), cfg)));
        
        bind(Router.class).toProvider(RouterProvider.class);
    }

    private TypeLiteral<?> from(Class<?> impl, PersistentConfig config) {
        return TypeUtils.of(impl, config.keyType(), config.valueType());
    }
}
