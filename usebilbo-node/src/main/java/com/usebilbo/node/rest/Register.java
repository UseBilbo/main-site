package com.usebilbo.node.rest;

import java.util.List;
import java.util.Optional;

import javax.cache.Cache.Entry;
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

        List<Entry<Long, AccountBase>> result = dao.query("email = ?", email).getAll();
        
        
        if (!result.isEmpty()) {
            sendError(response, 1);
            return;
        }
        
        Optional<AccountBase> account = dao.persist(AccountBase.of(email));
        
        if (!account.isPresent()) {
            sendError(response, 2);
            return;
        }
        
        //response.putHeader("content-type", "text/plain");
        //response.end("Hello World from vert-bootstrap! (" + email + ")");
        response.setStatusCode(HttpResponseStatus.FOUND.code());
        response.putHeader(HttpHeaders.LOCATION.toString(), "/index.html?id=" + account.get().getId());
        response.end();
    }

    private void sendError(HttpServerResponse response, int errorCode) {
        response.setStatusCode(HttpResponseStatus.FOUND.code());
        response.putHeader(HttpHeaders.LOCATION.toString(), "/index.html?err=" + errorCode);
        response.end();
    }
}
