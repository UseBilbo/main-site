package com.usebilbo.vertx.module.provider;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.properties.PropertyContainer;

import io.vertx.core.json.JsonObject;

@Singleton
public class JwtConfigProvider implements Provider<JsonObject> {
    private static final Logger LOG = LogManager.getLogger();
        
    private static final String PATH = "path";
    private static final String TYPE = "type";
    private static final String PASSWORD = "password";
    private static final String KEY_STORE = "keyStore";
    
    private final JsonObject config;

    @Inject
    public JwtConfigProvider(
            @Named("vertx.auth.jwt.type") PropertyContainer type,
            @Named("vertx.auth.jwt.path") PropertyContainer path,
            @Named("vertx.auth.jwt.password") PropertyContainer password
            ) {

        this.config = new JsonObject().put(KEY_STORE, new JsonObject()
                .put(PATH, path.asString())
                .put(TYPE, type.asString())
                .put(PASSWORD, password.asString()));
        LOG.info("Configuring JWT with type {} and keystore path {}", type.asString(), path.asString());
    }

    @Override
    public JsonObject get() {
        return config;
    }
}
