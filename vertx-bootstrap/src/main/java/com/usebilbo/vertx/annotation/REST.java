package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.util.Utils;

import io.vertx.core.http.HttpMethod;

@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface REST {
    String path() default Utils.NULL_STRING;
    
    String produces() default Utils.NULL_STRING;
    String consumes() default Utils.NULL_STRING;
    boolean blocking() default false;
    boolean useRegexp() default false;
    HttpMethod method() default HttpMethod.GET;
}
