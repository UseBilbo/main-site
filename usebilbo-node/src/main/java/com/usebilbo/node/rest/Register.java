package com.usebilbo.node.rest;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.usebilbo.node.domain.AccountBase;
import com.usebilbo.vertx.annotation.REST;
import com.usebilbo.vertx.annotation.RestRoute;
import com.usebilbo.vertx.cluster.api.CoreDao;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Singleton
@RestRoute("/api")
public class Register {
    private final CoreDao<Long, AccountBase> dao;

    @Inject
    public Register(CoreDao<Long, AccountBase> dao) {
        this.dao = dao;
    }
    
    @REST(method=HttpMethod.POST)
    public void register(RoutingContext routingContext) {
        String email = routingContext.request().getParam("email");
        HttpServerResponse response = routingContext.response();
        
        if (email == null) {
            response.setStatusCode(HttpResponseStatus.BAD_REQUEST.code());
            response.end();
            return;
        }
        
        
        
        //dao.
        
        //response.putHeader("content-type", "text/plain");
        //response.end("Hello World from vert-bootstrap! (" + email + ")");
        response.setStatusCode(HttpResponseStatus.FOUND.code());
        response.putHeader(HttpHeaders.LOCATION.toString(), "/index.html?id=" + email.hashCode());
        response.end();
    }
}
