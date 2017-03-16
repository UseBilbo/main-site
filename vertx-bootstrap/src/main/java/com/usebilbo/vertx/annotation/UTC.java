package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Helper annotation which marks system-wide UTC clock service and allows to distinguish it from other clock sources.
 */
@Documented
@Retention(RUNTIME)
@Target(PARAMETER)
@BindingAnnotation
public @interface UTC {
}
