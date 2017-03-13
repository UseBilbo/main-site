package com.usebilbo.vertx.web.configuration;

import javax.inject.Singleton;

import com.usebilbo.vertx.annotation.RouterConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.ext.web.Router;

@Singleton
@RouterConfiguration
public class RestConfigurator implements Configurator<Router> {

    @Override
    public void configure(Router options) {
        // TODO Auto-generated method stub
        
    }

}
