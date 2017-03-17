package com.usebilbo.vertx.web.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.usebilbo.vertx.exception.CoreException;
import com.usebilbo.vertx.web.configuration.RestMethod;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class RestMethodImpl implements RestMethod {
    private final String path;
    private final String consumes;
    private final String produces;
    private final boolean blocking;
    private final boolean regexp;
    private final HttpMethod method;
    private final Method beanMethod;

    public RestMethodImpl(Method beanMethod, String path, String consumes, String produces, boolean blocking,
            boolean regexp, HttpMethod method) {
        this.path = path;
        this.consumes = consumes;
        this.produces = produces;
        this.blocking = blocking;
        this.regexp = regexp;
        this.method = method;
        this.beanMethod = beanMethod;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public boolean isBlocking() {
        return blocking;
    }

    @Override
    public String consumes() {
        return consumes;
    }

    @Override
    public String produces() {
        return produces;
    }

    @Override
    public Object call(RoutingContext context, Object instance) {
        try {
            return beanMethod.invoke(instance, context);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new CoreException("Exception during invocation of method " + path, e);
        }
    }

    @Override
    public HttpMethod method() {
        return method;
    }

    @Override
    public boolean isRegexp() {
        return regexp;
    }
    
    @Override
    public String toString() {
        return path;
    }
}
