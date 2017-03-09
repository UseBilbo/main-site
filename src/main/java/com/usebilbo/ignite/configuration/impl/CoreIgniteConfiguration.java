package com.usebilbo.ignite.configuration.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

@Singleton
public class CoreIgniteConfiguration extends IgniteConfiguration {
    @Inject
    public CoreIgniteConfiguration(@GridConfigurator IgniteConfigurator configurator) {
        configurator.configure(this);
    }
}
