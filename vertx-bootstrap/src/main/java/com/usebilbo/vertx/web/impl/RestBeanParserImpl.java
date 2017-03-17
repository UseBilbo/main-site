package com.usebilbo.vertx.web.impl;

import static com.usebilbo.vertx.util.Utils.coalesce;
import static com.usebilbo.vertx.util.Utils.isEmpty;
import static com.usebilbo.vertx.util.Utils.orNull;
import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isNative;
import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.vertx.annotation.REST;
import com.usebilbo.vertx.annotation.RestRoute;
import com.usebilbo.vertx.cluster.api.BeanParser;
import com.usebilbo.vertx.util.Naming;
import com.usebilbo.vertx.web.configuration.RestBean;
import com.usebilbo.vertx.web.configuration.RestMethod;

import io.vertx.ext.web.RoutingContext;

public class RestBeanParserImpl implements BeanParser<RestBean> {
    private static final Logger LOG = LogManager.getLogger();
    private static final String ROOT = "/";
    private static final char SEPARATOR = '/';
    
    @Override
    public RestBean parse(Class<?> clazz) {
        RestRoute route = clazz.getAnnotation(RestRoute.class);
        
        if (route == null) {
            LOG.error("Missing @RestRoute annotation for class {}", clazz.getSimpleName());
            return null;
        }
        
        return new RestBeanImpl(clazz, normalizePath(route.path()), parseMethods(clazz, orNull(route.produces()), orNull(route.consumes())));
    }

    private List<RestMethod> parseMethods(Class<?> clazz, String beanProduces, String beanConsumes) {
        return Arrays.asList(clazz.getMethods()).stream()
                .filter(m -> isAcceptable(m))
                .map(m -> buildMethod(m, beanProduces, beanConsumes))
                .collect(Collectors.toList());
    }

    private RestMethod buildMethod(Method m, String beanProduces, String beanConsumes) {
        REST rest = m.getAnnotation(REST.class);
        
        return new RestMethodImpl(m, 
                buildParameter(rest.path(), Naming.name(m)), 
                buildParameter(rest.consumes(), beanConsumes), 
                buildParameter(rest.produces(), beanProduces), 
                rest.blocking(), 
                rest.useRegexp(), 
                rest.method());
    }

    private String buildParameter(String methodType, String beanType) {
        return coalesce(orNull(methodType), beanType);
    }

    private boolean isAcceptable(Method m) {
        return m.isAnnotationPresent(REST.class) && hasCorrectParameters(m) && hasCorrectType(m);
    }

    private boolean hasCorrectType(Method m) {
        int modifiers = m.getModifiers();
        return !(m.isSynthetic() || !m.isAccessible() || isNative(modifiers) || isAbstract(modifiers) || isStatic(modifiers) || isTransient(modifiers));
        
    }

    private boolean hasCorrectParameters(Method m) {
        Class<?>[] types = m.getParameterTypes();
        return types.length == 1 && types[0].isAssignableFrom(RoutingContext.class);
    }

    public static String normalizePath(String path) {
        String sanitized = coalesce(path, "").trim();
        
        if (isEmpty(sanitized)) {
            return ROOT;
        }
        
        if (sanitized.charAt(0) != SEPARATOR) {
            sanitized = ROOT + sanitized;
        }
        
        if (sanitized.length() > 1 && sanitized.charAt(sanitized.length() - 1) == SEPARATOR) {
            sanitized = sanitized.substring(0, sanitized.length() - 1);
        }
        
        return sanitized.trim();
    }
}
