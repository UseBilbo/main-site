package com.usebilbo.vertx.properties;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.usebilbo.vertx.properties.impl.PropertyContainerImpl;

public interface PropertyContainer {
    List<String> list();
    List<String> split();
    List<String> split(List<String> defaultValue);
    List<String> split(String pattern);

    Set<String> set();

    int asInt();
    int asInt(int defaultValue);
    long asLong();
    long asLong(long defaultValue);

    long asSpace();
    long asSpace(long defaultValue);

    long asDuration();
    long asDuration(long defaultValue);

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
