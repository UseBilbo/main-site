package com.usebilbo.annotation;

import java.lang.annotation.*;

/**
 * Marker for mandatory parameters and properties (defined by fields or methods).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
public @interface Mandatory {
}
