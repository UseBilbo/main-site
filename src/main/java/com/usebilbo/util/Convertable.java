package com.usebilbo.util;

public interface Convertable {
    String name();
    
    Class<?> type();
    Class<?> elementType();
    Class<?> implementation();

    boolean isRequired();
    boolean isMultivalue();
    boolean isArray();
}
