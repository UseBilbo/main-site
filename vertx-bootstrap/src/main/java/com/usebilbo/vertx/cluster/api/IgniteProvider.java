package com.usebilbo.vertx.cluster.api;

import javax.inject.Provider;

import org.apache.ignite.Ignite;


public interface IgniteProvider extends Provider<Ignite> {
    void set(Ignite ignite);
}
