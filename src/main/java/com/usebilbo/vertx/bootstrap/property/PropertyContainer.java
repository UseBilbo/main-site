package com.usebilbo.vertx.bootstrap.property;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.usebilbo.vertx.bootstrap.property.impl.PropertyContainerImpl;

public interface PropertyContainer {
    List<String> list();
    Set<String> split();
    Set<String> split(String pattern);

    Set<String> set();

    int asInt();
    int asInt(int defaultValue);
    long asLong();
    long asLong(long defaultValue);

    long asSpace();

    long asDuration();

    boolean asBool();
    boolean asBool(boolean defaultValue);

    String asString();
    String asString(String defaultValue);

    <T extends Enum<T>> T as(Class<T> clazz);
    <T extends Enum<T>> T as(Class<T> clazz, T defaultValue);

    public static PropertyContainer with(List<String> properties) {
        return new PropertyContainerImpl(properties);
    }

    public static PropertyContainer with(String... properties) {
        return new PropertyContainerImpl(Arrays.asList(properties));
    }
}
