package com.usebilbo.annotation;

import java.lang.annotation.*;

/**
 * This annotation is used to mark classes which should be part of persistent schema.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ConfigurationEntity(type=Object.class)
public @interface Persistent {
    String cacheName() default "";
    boolean transactional() default false;
}
