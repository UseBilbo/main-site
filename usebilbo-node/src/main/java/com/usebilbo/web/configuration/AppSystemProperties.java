package com.usebilbo.web.configuration;

import com.usebilbo.vertx.annotation.SystemProperties;
import com.usebilbo.vertx.configuration.SystemPropertiesConfigurator;

@SystemProperties
public class AppSystemProperties implements SystemPropertiesConfigurator {

    @Override
    public void configure() {
        //TODO: set these properties to actual values
//        System.setProperty("vertx.disableFileCaching", "true");
//        System.setProperty("java.util.logging.config.file", "true");
//        System.setProperty("java.specification.version", "true");
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "true");
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.threadFactory", "true");
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler", "true");
//        System.setProperty("vertx.logger-delegate-factory-class-name", "true");
//        System.setProperty("vertx.javaCompilerOptions", "true");
//        System.setProperty("vertx.cwd", "true");
//        System.setProperty("vertx.cluster.managerClass", "true");
//        System.setProperty("vertx.cli.usage.prefix", "true");
//        System.setProperty("vertx.cluster.public.host", "true");
//        System.setProperty("vertx.cluster.public.port", "true");
//        System.setProperty("vertx.cacheDirBase", "true");
    }
}
