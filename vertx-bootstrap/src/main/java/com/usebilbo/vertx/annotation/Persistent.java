package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.cluster.api.CoreDao;
import com.usebilbo.vertx.cluster.api.impl.CoreDaoImpl;

/**
 * This annotation is used to mark classes which should be part of persistent schema.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Persistent {
    boolean transactional() default false;
    
    Class<?> iface() default CoreDao.class;
    Class<?> impl() default CoreDaoImpl.class;
}
