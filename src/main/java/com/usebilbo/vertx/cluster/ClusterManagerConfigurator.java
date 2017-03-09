package com.usebilbo.vertx.cluster;

import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.VertxOptions;

@Singleton
@VertxOptionConfiguration
public class ClusterManagerConfigurator implements Configurator<VertxOptions> {

    //TODO: finish configuration of IgniteClusterManager
    @Override
    public void configure(VertxOptions options) {
        // TODO Auto-generated method stub
        
    }

}
