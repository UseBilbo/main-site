package com.usebilbo.vertx.web.security;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTOptions;

//TODO: need a replacement for JWTAuthProviderImpl with support of multiple versioned keys.
public class JWTMultikeyAuthProvider implements JWTAuth {

    @Override
    public void authenticate(JsonObject authInfo, Handler<AsyncResult<User>> resultHandler) {
        // TODO Auto-generated method stub

    }

    @Override
    public String generateToken(JsonObject claims, JWTOptions options) {
        // TODO Auto-generated method stub
        return null;
    }

}
