package com.usebilbo.vertx.cluster.api.impl;

import java.util.concurrent.atomic.AtomicReference;

import javax.inject.Singleton;

import org.apache.ignite.Ignite;

import com.usebilbo.vertx.cluster.api.IgniteProvider;

@Singleton
public class IgniteProviderImpl implements IgniteProvider {
    private static final AtomicReference<Ignite> ignite = new AtomicReference<>();

    @Override
    public Ignite get() {
        return ignite.get();
    }

    @Override
    public void set(Ignite ignite) {
        IgniteProviderImpl.ignite.set(ignite);
    }
}
