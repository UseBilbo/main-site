package com.usebilbo.vertx.module.configurator;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;

import io.vertx.core.http.HttpServerOptions;

@HttpOptionConfiguration
public class HttpOptionsConfigurator implements Configurator<HttpServerOptions> {

    @Override
    public void configure(HttpServerOptions options) {
        // TODO Auto-generated method stub
        
    }

}
