package com.usebilbo.vertx.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.usebilbo.vertx.util.Utils.LazyParamEvaluator;
import com.usebilbo.vertx.util.impl.FieldAdapterImpl;

public interface FieldAdapter extends Convertable {
    FieldAdapter set(Object bean, Object value) throws IllegalArgumentException, IllegalAccessException;
    
    <V> V get(Object bean) throws IllegalArgumentException, IllegalAccessException;
    
    /** Same as {@link #get(Object)} but returns <code>null</code> in case of error. */
    <V> V orNull(Object bean);
    
    /** Perform operation if retrieved field value is not null. Returns field value. */
    <F, V> F ifNotNull(Object bean, LazyParamEvaluator<F, V> operation);

    /** Perform operation if retrieved field value is null. <code>true</code> is returned if field was <code>null</code>. */
    <F, V> boolean ifNull(Object bean, LazyParamEvaluator<F, V> operation);
    
    Field field();
    
    public static FieldAdapter of(Field field) {
        return new FieldAdapterImpl(field);
    }

    boolean has(Class<? extends Annotation> annotation);
}
