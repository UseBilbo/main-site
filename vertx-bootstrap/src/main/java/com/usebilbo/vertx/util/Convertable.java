package com.usebilbo.vertx.util;

public interface Convertable {
    String name();
    
    Class<?> type();
    Class<?> elementType();
    Class<?> implementation();

    boolean isRequired();
    boolean isMultivalue();
    boolean isArray();
}
