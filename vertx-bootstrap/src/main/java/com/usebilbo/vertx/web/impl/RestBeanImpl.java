package com.usebilbo.vertx.web.impl;

import java.util.List;

import com.usebilbo.vertx.web.configuration.RestBean;
import com.usebilbo.vertx.web.configuration.RestMethod;

public class RestBeanImpl implements RestBean {
    private final Class<?> type;
    private final String path;
    private final List<RestMethod> methods;
    
    public RestBeanImpl(Class<?> type, String path, List<RestMethod> methods) {
        this.type = type;
        this.path = path;
        this.methods = methods;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public List<RestMethod> methods() {
        return methods;
    }

    @Override
    public Class<?> type() {
        return type;
    }

    @Override
    public String methodPath(String path) {
        return this.path + path;
    }

    @Override
    public String toString() {
        return this.path + " " + methods;
    }
}
