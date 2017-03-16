package com.usebilbo.vertx.cluster.configuration;

import javax.inject.Singleton;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.configuration.Configurator;

@Singleton
@ClusterConfigurator
public class ClusterLoggerConfigurator implements  Configurator<IgniteConfiguration> {

    @Override
    public void configure(IgniteConfiguration configuration) {
        IgniteLogger logger = new CoreIgniteLogger();
        configuration.setGridLogger(logger);
    }
}
