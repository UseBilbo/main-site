package com.usebilbo.ignite.configuration.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

@Singleton
@GridConfigurator
public class GridEventsConfigurator implements IgniteConfigurator {
    @Inject
    public GridEventsConfigurator() {
    }
    
    @Override
    public void configure(IgniteConfiguration configuration) {
        configuration.setIncludeEventTypes(EventType.EVT_CACHE_ENTRY_CREATED,
                                           EventType.EVT_CACHE_ENTRY_DESTROYED,
                                           EventType.EVT_CACHE_OBJECT_PUT,
                                           EventType.EVT_CACHE_OBJECT_REMOVED);
    }
}
