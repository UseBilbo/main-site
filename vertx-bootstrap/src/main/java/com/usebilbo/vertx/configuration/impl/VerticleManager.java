package com.usebilbo.vertx.configuration.impl;

import static com.usebilbo.vertx.util.Utils.ifNotNull;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.google.inject.Injector;
import com.usebilbo.vertx.annotation.AppVerticle;
import com.usebilbo.vertx.annotation.Options;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

//TODO: improve it. support configurable deployment (like cluster singleton, node singleton, etc.)
@Singleton
public class VerticleManager extends AbstractVerticle {
    private final Set<Class<?>> list;
    private final Vertx vertx;
    private final Injector injector;
    
    @Inject
    public VerticleManager(Reflections reflections, Vertx vertx, Injector injector) {
        this.vertx = vertx;
        this.injector = injector;
        this.list = reflections.getTypesAnnotatedWith(AppVerticle.class);
    }
    
    @Override
    public void start() {
        list.forEach((cls) -> deployVerticle(cls));
    }

    private void deployVerticle(Class<?> cls) {
        vertx.deployVerticle(getFullVerticleName(cls), buildDeploymentOptions(cls));
    }

    @SuppressWarnings("unchecked")
    private DeploymentOptions buildDeploymentOptions(Class<?> cls) {
        DeploymentOptions options = new DeploymentOptions();

        ifNotNull(cls.getAnnotation(Options.class), cfg -> {
                ((Configurator<DeploymentOptions>) injector.getInstance(cfg.value())).configure(options); 
                return null;
            });
        
        return options;
    }
    
    private static String getFullVerticleName(final Class<?> verticleClazz) {
        return GuiceVerticleFactory.PREFIX + ":" + verticleClazz.getCanonicalName();
    }
}
