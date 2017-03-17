package com.usebilbo.node.rest;

import com.usebilbo.vertx.annotation.REST;
import com.usebilbo.vertx.annotation.RestRoute;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@RestRoute("/api")
public class Register {
    @REST
    public void register(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "text/plain");
        response.end("Hello World from vert-bootstrap!");
    }
}
