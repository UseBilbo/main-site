package com.usebilbo.vertx.module.configurator;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.VertxOptions;
import io.vertx.core.dns.AddressResolverOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.metrics.MetricsOptions;
import io.vertx.core.spi.cluster.ClusterManager;

@Singleton
@VertxOptionConfiguration
public class VertxClusterManagerConfigurator implements Configurator<VertxOptions> {
    private final ClusterManager clusterManager;
    private final AddressResolverOptions addressResolverOptions;
    private final MetricsOptions metricsOptions;
    private final EventBusOptions eventBusOptions;

    @Inject
    public VertxClusterManagerConfigurator(ClusterManager clusterManager, 
            AddressResolverOptions addressResolverOptions,
            MetricsOptions metricsOptions,
            EventBusOptions eventBusOptions) {
        this.clusterManager = clusterManager;
        this.addressResolverOptions = addressResolverOptions;
        this.metricsOptions = metricsOptions;
        this.eventBusOptions = eventBusOptions;
    }

    @Override
    public void configure(VertxOptions options) {
        options.setClusterManager(clusterManager);
        options.setAddressResolverOptions(addressResolverOptions);
        options.setMetricsOptions(metricsOptions);
        options.setEventBusOptions(eventBusOptions);
    }
}
