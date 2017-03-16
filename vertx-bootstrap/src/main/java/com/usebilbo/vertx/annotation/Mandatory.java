package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Marker for mandatory parameters and properties (defined by fields or methods).
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD, METHOD})
public @interface Mandatory {
}
