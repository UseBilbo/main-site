package com.usebilbo.annotation;

import java.lang.annotation.*;

import com.google.inject.Guice;
import com.google.inject.Module;

/**
 * Application annotation (see {@link ConfigurationEntity} for more details)
 * which marks dependency injection configuration modules.
 *
 * @see Guice
 * @see Module
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ConfigurationEntity(type = Module.class)
public @interface ApplicationModule {
}
