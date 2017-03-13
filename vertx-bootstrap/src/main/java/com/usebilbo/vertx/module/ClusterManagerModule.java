package com.usebilbo.vertx.module;

import org.apache.ignite.configuration.CollectionConfiguration;

import com.google.inject.AbstractModule;
import com.usebilbo.vertx.annotation.BootModule;
import com.usebilbo.vertx.annotation.ClusterManagerQueue;
import com.usebilbo.vertx.cluster.manager.IgniteClusterManager;
import com.usebilbo.vertx.cluster.manager.impl.DefaultCollectionConfigurationProvider;
import com.usebilbo.vertx.module.provider.AddressResolverOptionsProvider;
import com.usebilbo.vertx.module.provider.EventBusOptionsProvider;
import com.usebilbo.vertx.module.provider.MetricsOptionsProvider;

import io.vertx.core.dns.AddressResolverOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;

@BootModule
public class ClusterManagerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(CollectionConfiguration.class).annotatedWith(ClusterManagerQueue.class)
                .toProvider(DefaultCollectionConfigurationProvider.class);
        bind(ClusterManager.class).to(IgniteClusterManager.class);
        bind(AddressResolverOptions.class).toProvider(AddressResolverOptionsProvider.class);
        bind(MetricsOptions.class).toProvider(MetricsOptionsProvider.class);
        bind(EventBusOptions.class).toProvider(EventBusOptionsProvider.class);
    }
}
