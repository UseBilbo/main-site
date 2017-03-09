package com.usebilbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.usebilbo.ignite.cache.CacheConfigurations;

/**
 * This annotation marks classes which are intended to configuration of system-wide known Ignite caches.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ConfigurationEntity(type=CacheConfigurations.class)
public @interface ClusterCacheConfigurations {
}
