package com.usebilbo.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public final class ListUtils {
    private ListUtils() {
    }

    public static <V> List<V> add(List<V> source, V value) {
        List<V> result = orEmpty(source);
        result.add(value);
        return result;
    }

    public static <V> List<V> remove(List<V> source, V value) {
        List<V> result = orEmpty(source);
        result.remove(value);
        return result;
    }

    public static <V> List<V> orEmpty(List<V> source) {
        return source == null ? new ArrayList<>() : source;
    }

    public static <T> List<T> toList(Enumeration<T> source) {
        List<T> result = new ArrayList<>();
        while (source.hasMoreElements()) {
            result.add(source.nextElement());
        }
        return result;
    }

    /**
     * Be careful when using this method to something other than
     * {@link ArrayList}, because of size calculation and direct element access.
     */
    public static <V> V last(List<V> source) {
        List<V> list = orEmpty(source);
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }
}
