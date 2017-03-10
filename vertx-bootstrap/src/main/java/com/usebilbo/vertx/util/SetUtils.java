package com.usebilbo.vertx.util;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public final class SetUtils {
    private SetUtils() {}
    
    public static <V> Set<V> orEmpty(Set<V> source) {
        return source == null ? new HashSet<>() : source;
    }

    public static <T extends Enum<T>> EnumSet<T> setOf(Class<T> type, T[] values) {
        EnumSet<T> result = EnumSet.noneOf(type);
    
        for (T role : values) {
            result.add(role);
        }
    
        return result;
    }
    
    public static <T> Set<T> merge(Set<T> s1, Set<T> s2) {
        Set<T> result = new HashSet<>();
        result.addAll(orEmpty(s1));
        result.addAll(orEmpty(s2));
        return result;
    }
    
    @SafeVarargs
    public static<T> Set<T> asSet(T ... elements) {
        Set<T> set = new HashSet<>();
        for (T t : elements) {
            set.add(t);
        }
        return set;
    }
}
