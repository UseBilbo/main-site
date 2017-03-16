package com.usebilbo.vertx.cluster.configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;

import com.usebilbo.vertx.annotation.ClusterConfigurator;
import com.usebilbo.vertx.configuration.Configurator;

@Singleton
@ClusterConfigurator
public class ClusterEventsConfigurator implements Configurator<IgniteConfiguration> {
    @Inject
    public ClusterEventsConfigurator() {
    }
    
    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setIncludeEventTypes(EventType.EVT_CACHE_ENTRY_CREATED,
                                           EventType.EVT_CACHE_ENTRY_DESTROYED,
                                           EventType.EVT_CACHE_OBJECT_PUT,
                                           EventType.EVT_CACHE_OBJECT_REMOVED);
    }
}
