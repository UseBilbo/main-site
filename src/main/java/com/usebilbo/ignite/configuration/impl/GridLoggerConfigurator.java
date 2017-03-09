package com.usebilbo.ignite.configuration.impl;

import javax.inject.Singleton;

import org.apache.ignite.IgniteLogger;
import org.apache.ignite.configuration.IgniteConfiguration;

import com.usebilbo.annotation.GridConfigurator;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

@Singleton
@GridConfigurator
public class GridLoggerConfigurator implements IgniteConfigurator {

    @Override
    public void configure(IgniteConfiguration configuration) {
        IgniteLogger logger = new CoreIgniteLogger();
        configuration.setGridLogger(logger);
    }
}
