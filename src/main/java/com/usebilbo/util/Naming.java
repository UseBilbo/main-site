package com.usebilbo.util;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.usebilbo.annotation.Description;
import static com.usebilbo.util.Utils.*;

public final class Naming {
    private Naming() {}

    public static String toString(Field field) {
        return new StringBuilder().append(field.getDeclaringClass().getSimpleName()).append('.').append(field.getName()).toString();
    }

    public static String description(AnnotatedElement element) {
        Description description = element.getAnnotation(Description.class);
        return description == null ? null : description.value();
    }

    public static String name(Class<?> clazz) {
        return coalesce(name((AnnotatedElement) clazz), clazz.getSimpleName());
    }

    public static String name(Field field) {
        return coalesce(name((AnnotatedElement) field), field.getName());
    }

    public static String name(Executable field) {
        return coalesce(name((AnnotatedElement) field), field.getName());
    }

    private static String name(AnnotatedElement name) {
        com.google.inject.name.Named n1 = name.getAnnotation(com.google.inject.name.Named.class);
        javax.inject.Named n2 = name.getAnnotation(javax.inject.Named.class);

        if (n1 != null) {
            return n1.value();
        } else if (n2 != null) {
            return n2.value();
        } else {
            return null;
        }
    }

    public static <E> String classNames(List<E> instances) {
        return String.join(", ", coalesce(instances, Collections.emptyList()).stream().map(m -> m.getClass().getSimpleName()).collect(Collectors.toList()));
    }

    public static <E> String names(List<Class<? extends E>> classes) {
        return String.join(", ", coalesce(classes, Collections.<Class<? extends E>>emptyList()).stream().map(m -> m.getSimpleName()).collect(Collectors.toList()));
    }
}
