package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

@Documented
@Retention(RUNTIME)
@Target({TYPE, METHOD})
@BindingAnnotation
public @interface JWT {
}
