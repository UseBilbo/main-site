package com.usebilbo.vertx.web;

import java.net.URI;

import javax.inject.Inject;
import javax.inject.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.annotation.AppVerticle;
import com.usebilbo.vertx.annotation.Options;
import com.usebilbo.vertx.util.Utils;
import com.usebilbo.vertx.util.WebUtils;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

@AppVerticle
@Options(ha=true)
public class RedirectorVerticle extends AbstractVerticle {
    private static final Logger LOG = LogManager.getLogger();

    private static final int DEFAULT_HTTP_PORT = 80;
    
    private final HttpServerOptions options;
    private final boolean enableRedirector;
    private final int publicHttpsPort;
    
    @Inject
    public RedirectorVerticle(HttpServerOptions options, Provider<Router> routerProvider) {
        this.enableRedirector = options.getPort() != DEFAULT_HTTP_PORT;
        this.publicHttpsPort = options.getPort();
        this.options = new HttpServerOptions(options).setSsl(false).setUseAlpn(false).setPort(DEFAULT_HTTP_PORT);
    }

    @Override
    public void start() {
        if (!enableRedirector) {
            return;
        }
        Router router = Router.router(vertx);
        router.route().handler(this::handleRedirect);
        vertx.createHttpServer(options).requestHandler(router::accept).listen(options.getPort());

        LOG.info("HTTP redirector is listening at " + options.getPort());
    }
    
    void handleRedirect(RoutingContext context) {
        String proto = Utils.coalesce(context.request().getHeader("X-Forwarded-Proto"), 
                                      context.request().getHeader("X-Forwarded-Scheme"));
        if ((Utils.isEmpty(proto) && context.request().isSSL()) || "https".equalsIgnoreCase(proto)) {
            context.next();
        } else {
            try {
                URI myPublicUri = new URI(context.request().absoluteURI());
                URI myHttpsPublicUri = new URI("https", 
                        myPublicUri.getUserInfo(), 
                        myPublicUri.getHost(),
                        publicHttpsPort,
                        myPublicUri.getRawPath(), 
                        myPublicUri.getRawQuery(), 
                        myPublicUri.getRawFragment());
                
                WebUtils.sendRedirect(context.response(), myHttpsPublicUri.toString());
            } catch (Throwable ex) {
                context.fail(ex);
            }            
        }
    }
}
