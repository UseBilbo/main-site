package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** 
 * Helper annotation used to mark generic types of parameters.
 */
@Documented
@Retention(RUNTIME)
@Target({PARAMETER, FIELD})
public @interface Typed {
    Class<?> value();
}
