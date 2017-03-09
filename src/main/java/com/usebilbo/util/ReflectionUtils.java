package com.usebilbo.util;

import com.usebilbo.exception.CoreException;

public final class ReflectionUtils {
    private ReflectionUtils() {}

    public static<T> T newInstance(Class<? extends T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CoreException("Error while instantianing " + clazz.getName(), e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> rawClassByName(String name) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            return (Class<T>) loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
