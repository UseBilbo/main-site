package com.usebilbo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to make application annotations recognized by
 * bootstrapping process. Application annotations are used then to build
 * application configuration. See {@link ApplicationModule} for example.
 * <p>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.ANNOTATION_TYPE })
public @interface ConfigurationEntity {
    /**
     * Base class of the types annotated with application annotation. If class
     * is annotated with application annotation it must implement or inherit
     * type provided here.
     */
    Class<?> type();
}
