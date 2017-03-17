package com.usebilbo.vertx.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.usebilbo.vertx.util.Utils;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.VertxOptions;

/**
 * This annotation is used when non-default deployment options are necessary for the verticle.
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Options {
    boolean worker() default DeploymentOptions.DEFAULT_WORKER;
    boolean multiThreaded() default DeploymentOptions.DEFAULT_MULTI_THREADED;
    String isolationGroup() default Utils.NULL_STRING;
    String workerPoolName() default Utils.NULL_STRING;
    int workerPoolSize() default VertxOptions.DEFAULT_WORKER_POOL_SIZE;
    long maxWorkerExecuteTime() default VertxOptions.DEFAULT_MAX_WORKER_EXECUTE_TIME;
    boolean ha() default DeploymentOptions.DEFAULT_HA;
    int instances() default DeploymentOptions.DEFAULT_INSTANCES;
}
