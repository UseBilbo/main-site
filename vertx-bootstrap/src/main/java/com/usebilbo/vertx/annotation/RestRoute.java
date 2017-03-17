package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.util.Utils;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface RestRoute {
    String path();
    
    String produces() default Utils.NULL_STRING;
    String consumes() default Utils.NULL_STRING;
}
