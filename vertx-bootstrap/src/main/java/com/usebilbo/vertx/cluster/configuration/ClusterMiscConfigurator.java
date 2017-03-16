package com.usebilbo.vertx.cluster.configuration;

import static com.usebilbo.vertx.util.Utils.coalesce;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;

import com.google.inject.name.Named;
import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

@Singleton
@ClusterConfigurator
public class ClusterMiscConfigurator implements  Configurator<IgniteConfiguration> {
    private static final String DEFAULT_GRID_NAME = "dev";

    private final String gridName;
    private final long logFrequency;

    @Inject
    public ClusterMiscConfigurator(@Named("vertx.cluster.name") PropertyContainer gridName,
                                @Named("vertx.cluster.metrics.log.frequency") PropertyContainer logFrequency) {
        this.gridName = gridName.asString();
        this.logFrequency = logFrequency.asDuration();
    }
    
    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setGridName(coalesce(gridName, DEFAULT_GRID_NAME));
        configuration.setMetricsLogFrequency(logFrequency);
    }
}
