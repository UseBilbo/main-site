package com.usebilbo.util;

import com.google.inject.TypeLiteral;
import com.google.inject.internal.MoreTypes;

public final class TypeUtils {
    private TypeUtils() {}

    @SuppressWarnings("unchecked")
    public static<T> TypeLiteral<T> of(Class<T> baseClass, Class<?>... keys) {
        return (TypeLiteral<T>) TypeLiteral.get(new MoreTypes.ParameterizedTypeImpl(null, baseClass, keys));
    }
}
