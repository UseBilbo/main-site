package com.usebilbo.vertx.cluster;

import java.util.List;

import org.apache.ignite.configuration.CacheConfiguration;

public interface CacheConfigurations {
    List<CacheConfiguration<?, ?>> get();
}
