package com.usebilbo.ignite.configuration.impl;

import static com.usebilbo.util.Utils.coalesce;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;

import com.google.inject.name.Named;
import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;
import com.usebilbo.vertx.bootstrap.property.PropertyContainer;

@Singleton
@GridConfigurator
public class GridMiscConfigurator implements IgniteConfigurator {
    private static final String DEFAULT_GRID_NAME = "dev";

    private final String gridName;
    private final long logFrequency;

    @Inject
    public GridMiscConfigurator(@Named("grid.name") PropertyContainer gridName,
                                @Named("grid.metrics.log.frequency") PropertyContainer logFrequency) {
        this.gridName = gridName.asString();
        this.logFrequency = logFrequency.asDuration();
    }
    
    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setGridName(coalesce(gridName, DEFAULT_GRID_NAME));
        configuration.setMetricsLogFrequency(logFrequency);
    }
}
