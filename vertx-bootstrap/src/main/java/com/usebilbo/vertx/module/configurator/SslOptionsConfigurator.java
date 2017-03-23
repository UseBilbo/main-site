package com.usebilbo.vertx.module.configurator;

import javax.inject.Inject;
import javax.inject.Named;

import com.usebilbo.vertx.annotation.HttpOptionConfiguration;
import com.usebilbo.vertx.configuration.Configurator;
import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.core.net.PfxOptions;

@HttpOptionConfiguration
public class SslOptionsConfigurator implements Configurator<HttpServerOptions> {
    //JKS/PFX
    private final String password;
    private final String path;
    //PEM  
    private final String keyPath;
    private final String certPath;
    private final CertType type;

    @Inject
    public SslOptionsConfigurator(
            @Named("vertx.ssl.password") PropertyContainer password,
            @Named("vertx.ssl.path"    ) PropertyContainer path,   
            @Named("vertx.ssl.keyPath" ) PropertyContainer keyPath,
            @Named("vertx.ssl.certPath") PropertyContainer certPath,
            @Named("vertx.ssl.type"    ) PropertyContainer type) {
        this.password = password.asString();
        this.path     = path    .asString();
        this.keyPath  = keyPath .asString();
        this.certPath = certPath.asString();
        this.type = type.as(CertType.class, CertType.NONE);
    }

    @Override
    public void configure(HttpServerOptions options) {
        switch(type) {
        case JKS:
            options.setKeyStoreOptions(new JksOptions().setPassword(password).setPath(path));
            break;
        case PEM:
            options.setPemKeyCertOptions(new PemKeyCertOptions().setCertPath(certPath).setKeyPath(keyPath));
            break;
        case PFX:
            options.setPfxKeyCertOptions(new PfxOptions().setPassword(password).setPath(path));
            break;
        case NONE:
        default:
            break;
        }
    }
}
