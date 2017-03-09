package com.usebilbo.util;

import static java.lang.reflect.Modifier.isAbstract;
import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.ReflectionUtils;

import com.usebilbo.annotation.Typed;
import com.usebilbo.exception.CoreException;

public final class ClassUtils {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final Set<Class<?>> KNOWN_TYPES = new HashSet<>();
    
    static {
        KNOWN_TYPES.addAll(Arrays.asList(boolean.class, byte.class, char.class, short.class, int.class, long.class, float.class, double.class));
        KNOWN_TYPES.addAll(Arrays.asList(String.class, Date.class, java.sql.Date.class, Timestamp.class));
        KNOWN_TYPES.addAll(Arrays.asList(ArrayList.class, LinkedList.class, HashSet.class, LinkedHashSet.class, TreeSet.class));
    }

    private ClassUtils() {}

    
    public static interface ClassToInstanceMapper<K> {
        K map(Class<K> key);
    }
    
    public static void addKnownTypes(Collection<Class<?>> knownTypes) {
        KNOWN_TYPES.addAll(knownTypes);
    }

    public static boolean isIncompleteCollectionType(Class<?> type) {
        return Collection.class.isAssignableFrom(type) && isIncomplete(type);
    }

    public static boolean isIncomplete(Class<?> type) {
        return type.isInterface() || isAbstract(type.getModifiers());
    }

    public static Class<?> detectImplementation(Class<?> type, Object parameter) {
        if (type.isArray() || isKnownType(type) || !isIncomplete(type)) {
            return type;
        }
        
        if (isIncompleteCollectionType(type)) {
            if (Set.class.isAssignableFrom(type)) {
                return LinkedHashSet.class;
            }
            
            if (List.class.isAssignableFrom(type) || Collection.class.isAssignableFrom(type)) {
                return ArrayList.class;
            }
        }
        
        throw new CoreException("Unable to find suitable implementation type for " + parameter + " of type " + type.getName());
    }

    private static boolean isKnownType(Class<?> type) {
        return KNOWN_TYPES.contains(type);
    }

    public static Class<?> detectElementType(Parameter parameter) {
        return detectElementType(parameter.getType(), parameter, (k) -> parameter.getAnnotation(k));
    }
    
    public static Class<?> detectElementType(Field field) {
        return detectElementType(field.getType(), field, (k) -> field.getAnnotation(k));
    }
    
    public static Class<?> detectElementType(Class<?> type, Object target, ClassToInstanceMapper<Typed> acc) {
        if (type.isArray())
            return type.getComponentType();
        
        Typed typed = acc.map(Typed.class);
        
        if (typed != null) {
            return typed.value();
        }
        
        if (target instanceof Field) {
            Field field = (Field) target;
            Type genericType = field.getGenericType();
            
            if (genericType instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) genericType).getActualTypeArguments();
                if (args != null && args.length > 0 && args[0] instanceof Class) {
                    return (Class<?>) args[0];
                }
            }
            
            if (isMultiValue(type)) {
                LOG.warn("Evement type for {} can't be determined, assuming Object.class", Naming.toString(field));
                return Object.class;
            }
        }
        
        return null;
    }

    public static boolean isMultiValue(Class<?> type) {
        return type.isArray() || Collection.class.isAssignableFrom(type);
    }

    public static List<Field> annotatedFields(Class<?> valueType, final Class<? extends Annotation> clazz) {
        if (valueType == Object.class || valueType == null || clazz == null) {
            return Collections.emptyList();
        }
        
        List<Field> list = Arrays.asList(valueType.getDeclaredFields()).stream()
                .filter(f -> isAcceptable(f, clazz))
                .collect(Collectors.toList());
        list.addAll(annotatedFields(valueType.getSuperclass(), clazz));
        return list;
    }

    public static boolean isAcceptable(Field field, Class<? extends Annotation> clazz) {
        int m = field.getModifiers();
        
        return (field.isSynthetic() 
                || isFinal(m) 
                || isStatic(m)) ? false : field.isAnnotationPresent(clazz);
    }

    public static <T> Stream<Field> fieldsOf(Class<T> clazz) {
        return fieldSetOf(clazz).stream();
    }

    @SuppressWarnings("unchecked")
    public static <T> Set<Field> fieldSetOf(Class<T> clazz) {
        return ReflectionUtils.getAllFields(clazz, f -> acceptField(f));
    }
    
    private static boolean acceptField(Field f) {
        int modifiers = f.getModifiers();
        boolean result = f.isSynthetic() 
                || Modifier.isNative(modifiers) 
                || Modifier.isFinal(modifiers) 
                || Modifier.isAbstract(modifiers) 
                || Modifier.isStatic(modifiers)
                || Modifier.isTransient(modifiers)
                ; 
        return !result;
    }
}
