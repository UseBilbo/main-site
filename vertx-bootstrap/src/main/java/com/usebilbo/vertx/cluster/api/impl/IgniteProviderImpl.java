package com.usebilbo.vertx.cluster.api.impl;

import java.util.concurrent.atomic.AtomicMarkableReference;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;

@Singleton
public class IgniteProviderImpl implements Provider<Ignite> {
    private final AtomicMarkableReference<Ignite> ignite = new AtomicMarkableReference<>(null, false);
    private final IgniteConfiguration configuration;
    
    @Inject
    public IgniteProviderImpl(IgniteConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Ignite get() {
        if (ignite.isMarked()) {
            return ignite.getReference();
        }
        
        ignite.set(Ignition.start(configuration), true);
        return ignite.getReference();
    }
}
