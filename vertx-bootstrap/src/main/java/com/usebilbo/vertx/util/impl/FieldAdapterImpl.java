package com.usebilbo.vertx.util.impl;

import static com.usebilbo.vertx.util.ClassUtils.detectElementType;
import static com.usebilbo.vertx.util.ClassUtils.detectImplementation;
import static com.usebilbo.vertx.util.ClassUtils.isMultiValue;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.usebilbo.vertx.annotation.Mandatory;
import com.usebilbo.vertx.util.FieldAdapter;
import com.usebilbo.vertx.util.Utils.LazyParamEvaluator;

public class FieldAdapterImpl implements FieldAdapter {
    private final Field field;
    private final Class<?> elementType;
    private final Class<?> implementation;
    private final boolean mutivalue;
    private final boolean required;

    public FieldAdapterImpl(Field field) {
        this.field = field;
        this.field.setAccessible(true);
        this.implementation = detectImplementation(field.getType(), field);
        this.mutivalue = isMultiValue(field.getType());
        this.required = field.isAnnotationPresent(Mandatory.class);
        this.elementType = detectElementType(field);
    }
    
    @Override
    public Field field() {
        return field;
    }

    @Override
    public String name() {
        return field.getName();
    }

    @Override
    public Class<?> type() {
        return field.getType();
    }

    @Override
    public FieldAdapter set(Object bean, Object value) throws IllegalArgumentException, IllegalAccessException {
        field.set(bean, value);
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(Object bean) throws IllegalArgumentException, IllegalAccessException {
        return (V) field.get(bean);
    }
    
    @Override
    public <V> V orNull(Object bean) {
        try {
            return bean != null ? get(bean) : null;
        } catch (IllegalArgumentException | IllegalAccessException e) {
            return null;
        }
    }

    @Override
    public <F, V> F ifNotNull(Object bean, LazyParamEvaluator<F, V> operation) {
        V val = orNull(bean);
        return val != null ? operation.eval(val) : null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <F, V> boolean ifNull(Object bean, LazyParamEvaluator<F, V> operation) {
        if (orNull(bean) != null) {
        	return false;
		}

        try {
        	V value = (V) bean;
        	set(value, operation.eval(value));
        	return true;
        } catch (IllegalArgumentException | IllegalAccessException e) {
        	return true;
        }
    }
    
    @Override
    public boolean isRequired() {
        return required;
    }

    @Override
    public boolean isMultivalue() {
        return mutivalue;
    }

    @Override
    public Class<?> elementType() {
        return elementType;
    }

    @Override
    public boolean isArray() {
        return type().isArray();
    }

    @Override
    public Class<?> implementation() {
        return implementation;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().append(field.getDeclaringClass().getSimpleName()).append('.').append(field.getName()).toString();
    }

    @Override
    public boolean has(Class<? extends Annotation> ann) {
        return field.isAnnotationPresent(ann);
    }
}
