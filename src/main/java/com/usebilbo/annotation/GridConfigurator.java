package com.usebilbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;
import com.usebilbo.ignite.configuration.IgniteConfigurator;

/**
 * This annotation is used to mark instances of classes which perform configuration of the grid before it starts.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
@BindingAnnotation
@ConfigurationEntity(type=IgniteConfigurator.class)
public @interface GridConfigurator {
}
