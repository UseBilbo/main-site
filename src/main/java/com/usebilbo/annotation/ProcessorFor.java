package com.usebilbo.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify processors for annotations used as
 * {@link ConfigurationEntity}.
 * 
 * <pre>
 * {@code @ProcessorFor(ApplicationModule.class)
 * public class ModuleProcessor extends AbstractBootProcessor<ApplicationModule, Module>
 * }
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface ProcessorFor {
    Class<? extends Annotation> value();
}