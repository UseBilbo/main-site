package com.usebilbo.node.rest;

import static com.usebilbo.vertx.util.WebUtils.sendRedirect;
import static com.usebilbo.vertx.util.Utils.*;
import static com.usebilbo.vertx.util.CvtUtils.*;

import java.util.List;
import java.util.Optional;

import javax.cache.Cache.Entry;
import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.node.domain.AccountBase;
import com.usebilbo.vertx.annotation.REST;
import com.usebilbo.vertx.annotation.RestRoute;
import com.usebilbo.vertx.annotation.UTC;
import com.usebilbo.vertx.cluster.api.CoreDao;
import com.usebilbo.vertx.util.ClockService;

import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

@Singleton
@RestRoute("/api")
public class Register {
    private static final Logger LOG = LogManager.getLogger();
    
    private final CoreDao<Long, AccountBase> dao;
    private final ClockService clockService;

    @Inject
    public Register(CoreDao<Long, AccountBase> dao, @UTC ClockService clockService) {
        this.dao = dao;
        this.clockService = clockService;
    }
    
    @REST(method=HttpMethod.POST)
    public void register(RoutingContext routingContext) {
        String email = routingContext.request().getParam("email");
        LOG.info("Requested {} {}", routingContext.normalisedPath(), coalesce(email, ""));
        
        HttpServerResponse response = routingContext.response();
        
        if (email == null || !validateEmail(email)) {
            sendError(response, 1);
            return;
        }

        List<Entry<Long, AccountBase>> result = dao.query("email = ?", email).getAll();
        
        
        if (!result.isEmpty()) {
            sendError(response, 2);
            return;
        }
        
        Optional<AccountBase> account = dao.persist(AccountBase.of(email).setTimestamp(clockService.now()));
        
        if (!account.isPresent()) {
            sendError(response, 3);
            return;
        }
        
        sendRedirect(response, "/index.html?id=" + account.get().getId());
        LOG.info("Created new account {} for {}", account.get().getId(), account.get().getEmail());
    }

    private void sendError(HttpServerResponse response, int errorCode) {
        sendRedirect(response, "/index.html?err=" + errorCode);
    }
}
