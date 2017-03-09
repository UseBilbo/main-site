package com.usebilbo.ignite.api;

import javax.inject.Provider;

import org.apache.ignite.Ignite;


public interface IgniteProvider extends Provider<Ignite> {
    void set(Ignite ignite);
}
