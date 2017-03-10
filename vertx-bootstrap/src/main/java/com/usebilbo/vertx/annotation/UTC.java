package com.usebilbo.vertx.annotation;

import java.lang.annotation.*;

import com.google.inject.BindingAnnotation;

/**
 * Helper annotation which marks system-wide UTC clock service and allows to distinguish it from other clock sources.
 */
//TODO: add to bootstrap module
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@BindingAnnotation
public @interface UTC {
}
