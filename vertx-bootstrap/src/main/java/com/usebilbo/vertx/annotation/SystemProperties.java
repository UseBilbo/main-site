package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.configuration.SystemPropertiesConfigurator;

/**
 * This annotation is used to mark classes which configure system properties.
 * Class itself should implement {@link SystemPropertiesConfigurator}.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface SystemProperties {
}
