package com.usebilbo.vertx.configuration.impl;

import static com.usebilbo.vertx.util.Utils.ifNotNull;
import static com.usebilbo.vertx.util.Utils.orNull;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.AppVerticle;
import com.usebilbo.vertx.annotation.Options;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

@Singleton
public class VerticleManager extends AbstractVerticle {
    private final Set<Class<?>> list;
    private final Vertx vertx;

    @Inject
    public VerticleManager(Reflections reflections, Vertx vertx) {
        this.vertx = vertx;
        this.list = reflections.getTypesAnnotatedWith(AppVerticle.class);
    }

    @Override
    public void start() {
        list.forEach((cls) -> deployVerticle(cls));
    }

    private void deployVerticle(Class<?> cls) {
        vertx.deployVerticle(getFullVerticleName(cls), buildDeploymentOptions(cls));
    }

    private DeploymentOptions buildDeploymentOptions(Class<?> cls) {
        DeploymentOptions options = new DeploymentOptions();

        ifNotNull(cls.getAnnotation(Options.class), cfg -> {
            options.setWorker(cfg.worker());
            options.setMultiThreaded(cfg.multiThreaded());
            options.setHa(cfg.ha());
            options.setWorkerPoolSize(cfg.workerPoolSize());
            options.setInstances(cfg.instances());
            options.setMaxWorkerExecuteTime(cfg.maxWorkerExecuteTime());
            options.setIsolationGroup(orNull(cfg.isolationGroup()));
            options.setWorkerPoolName(orNull(cfg.workerPoolName()));
            return null;
        });

        return options;
    }

    private static String getFullVerticleName(final Class<?> verticleClazz) {
        return GuiceVerticleFactory.PREFIX + ":" + verticleClazz.getCanonicalName();
    }
}
