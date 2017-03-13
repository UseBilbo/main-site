package com.usebilbo.vertx.cluster.configuration;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.vertx.annotation.VertxOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.VertxOptions;
import io.vertx.spi.cluster.ignite.IgniteClusterManager;

@Singleton
@VertxOptionConfiguration
public class ClusterManagerConfigurator implements Configurator<VertxOptions> {
    private final Provider<Ignite> provider;
    
    @Inject
    public ClusterManagerConfigurator(Provider<Ignite> provider) {
        this.provider = provider;
    }

    @Override
    public void configure(VertxOptions options) {
        IgniteConfiguration cfg = new IgniteConfiguration();
        IgniteClusterManager manager = new IgniteClusterManager(cfg);
        //manager.
        System.setProperty("IGNITE_NO_SHUTDOWN_HOOK", "true");
        
        // TODO Auto-generated method stub
        
    }

}
