package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker for the Ignite configuration handlers.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface ClusterConfigurator {
}
