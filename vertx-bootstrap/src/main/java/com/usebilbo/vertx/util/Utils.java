package com.usebilbo.vertx.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public final class Utils {
    public static final String NULL_STRING = "null";
    private static final int BUFFER_SIZE = 16384;

    private Utils() {
    }
    
    public static String orNull(String nullable) {
        return NULL_STRING.equalsIgnoreCase(nullable) ? null : nullable;
    }

    public static <T> T coalesce(T obj1, T obj2) {
        return (obj1 != null) ? obj1 : obj2;
    }

    public static <T> T coalesce(T obj1, T obj2, T obj3) {
        return coalesce(coalesce(obj1, obj2), obj3);
    }

    public static <T> T coalesce(T obj1, T obj2, T obj3, T obj4) {
        return coalesce(coalesce(obj1, obj2, obj3), obj4);
    }

    public static <T> T coalesce(LazyEvaluator<T> value1, LazyEvaluator<T> value2) {
        T obj1 = value1.eval();
        return (obj1 != null) ? obj1 : value2.eval();
    }

    public static <T> T ifNotNull(Object obj1, LazyEvaluator<T> value1) {
        return (obj1 != null) ? value1.eval() : null;
    }

    public static <T, P> T ifNotNull(P obj1, LazyParamEvaluator<T, P> value1) {
        return (obj1 != null) ? value1.eval(obj1) : null;
    }

    public static <P> P doIfNotNull(P obj1, LazyInvoke<P> value1) {
        if(obj1 != null) {
            value1.eval(obj1);
        }
        return obj1;
    }
    
    public static <T> T ifNotNull(Object obj1, LazyEvaluator<T> value1, LazyEvaluator<T> value2) {
        return (obj1 != null) ? value1.eval() : value2.eval();
    }

    public static interface LazyEvaluator<T> {
        T eval();
    }

    public static interface LazyInvoke<T> {
        void eval(T param);
    }

    public static interface LazyParamEvaluator<T, P> {
        T eval(P param);
    }

    public static String[] merge(String[] sourcePackages, String... names) {
        String[] packages = coalesce(sourcePackages, new String[0]);
        String[] result = Arrays.copyOf(packages, packages.length + names.length, String[].class);
        int i = 0;
        for (String name : names) {
            result[packages.length + i++] = name;
        }
        return result;
    }

    public static String parentPackage(String pkg) {
        if (isEmpty(pkg)) {
            return pkg;
        }
        int ndx = pkg.lastIndexOf('.');
        if (ndx < 0) {
            return pkg;
        }
        return pkg.substring(0, ndx);
    }

    public static boolean isEmpty(CharSequence string) {
        return string == null || string.length() == 0;
    }

    public static List<String> split(String source, String expr) {
        return Arrays.asList(coalesce(source, "").split(expr));
    }

    public static Path from(String source, String... more) {
        return FileSystems.getDefault().getPath(source, more);
    }

    public static long copy(InputStream source, OutputStream sink) throws IOException {
        long nread = 0L;
        byte[] buf = new byte[BUFFER_SIZE];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
            nread += n;
        }
        return nread;
    }
}
