package com.usebilbo.node.rest;

import com.usebilbo.vertx.annotation.REST;
import com.usebilbo.vertx.annotation.RestRoute;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@RestRoute("/api")
public class Register {
    @REST(method=HttpMethod.POST)
    public void register(RoutingContext routingContext) {
        String email = routingContext.request().getParam("email");
        HttpServerResponse response = routingContext.response();
        //response.putHeader("content-type", "text/plain");
        //response.end("Hello World from vert-bootstrap! (" + email + ")");
        response.setStatusCode(HttpResponseStatus.FOUND.code());
        response.putHeader(HttpHeaders.LOCATION.toString(), "/index.html?id=" + email.hashCode());
        response.end();
    }
}
