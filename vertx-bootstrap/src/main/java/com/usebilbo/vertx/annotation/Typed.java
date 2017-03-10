package com.usebilbo.vertx.annotation;

import java.lang.annotation.*;

/** 
 * Helper annotation used to mark generic types of parameters.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
public @interface Typed {
    Class<?> value();
}
