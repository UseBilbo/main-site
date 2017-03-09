package com.usebilbo.vertx;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reflections.Reflections;

import com.usebilbo.vertx.annotation.AppVerticle;

import io.vertx.core.AbstractVerticle;

//TODO: finish it.
@Singleton
public class VerticleManager  extends AbstractVerticle {
    @Inject
    public VerticleManager(Reflections reflections) {
        Set<Class<?>> list = reflections.getTypesAnnotatedWith(AppVerticle.class);
    }
    
    
    @Override
    public void start() {
    }

}
