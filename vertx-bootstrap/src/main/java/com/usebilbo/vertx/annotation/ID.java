package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Entity ID 
 */
@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface ID {
}
