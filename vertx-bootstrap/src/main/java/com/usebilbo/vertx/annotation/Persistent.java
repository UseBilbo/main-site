package com.usebilbo.vertx.annotation;

import java.lang.annotation.*;

/**
 * This annotation is used to mark classes which should be part of persistent schema.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Persistent {
    String cacheName() default "";
    boolean transactional() default false;
}
