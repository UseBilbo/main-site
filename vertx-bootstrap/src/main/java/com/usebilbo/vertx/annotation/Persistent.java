package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark classes which should be part of persistent schema.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Persistent {
    String cacheName() default "";
    boolean transactional() default false;
}
