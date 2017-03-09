package com.usebilbo.annotation;

import java.lang.annotation.*;

/**
 * Entity ID 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ID {
}
