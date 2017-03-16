package com.usebilbo.vertx.web.configuration;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public interface RestMethod {

    String path();

    boolean isBlocking();

    String consumes();
    
    String produces();
    
    Object call(RoutingContext context, Object instance);

    HttpMethod method();

    boolean isRegexp();
}
