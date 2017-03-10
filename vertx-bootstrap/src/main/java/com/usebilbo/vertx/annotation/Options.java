package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.configuration.Configurator;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Options {
    Class<? extends Configurator<?>> value() ;
}
