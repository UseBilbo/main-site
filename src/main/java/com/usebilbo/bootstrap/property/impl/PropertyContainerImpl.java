package com.usebilbo.bootstrap.property.impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.usebilbo.bootstrap.property.PropertyContainer;
import com.usebilbo.util.CvtUtils;
import com.usebilbo.util.ListUtils;
import com.usebilbo.util.Utils;

public class PropertyContainerImpl implements PropertyContainer {
    private static final Logger LOG = LogManager.getLogger();

    private static final String SPACE = " ";
    
    private final List<String> values;

    public PropertyContainerImpl(List<String> values) {
        this.values = values;
    }

    @Override
    public List<String> list() {
        return new ArrayList<>(values);
    }

    @Override
    public Set<String> set() {
        return new LinkedHashSet<>(values);
    }

    @Override
    public <T extends Enum<T>> T as(Class<T> clazz) {
        return as(clazz, null);
    }

    @Override
    public <T extends Enum<T>> T as(Class<T> clazz, T defaultValue) {
        if (values.isEmpty()) {
            return defaultValue;
        }
        
        try {
            return Enum.valueOf(clazz, getLast().toUpperCase(Locale.US));
        } catch (IllegalArgumentException e) {
            LOG.warn("Unable to parse value {} of type {}, using default {}", getLast(), clazz.getName(), defaultValue);
            return defaultValue;
        }
    }

    private String getLast() {
        return ListUtils.last(values);
    }

    @Override
    public int asInt() {
        return asInt(0);
    }

    @Override
    public long asLong() {
        return asLong(0);
    }

    @Override
    public String asString() {
        return asString(null);
    }
    
    @Override
    public String asString(String defaultValue) {
        return values.isEmpty() ? defaultValue : getLast();
    }

    @Override
    public int asInt(int defaultValue) {
        return CvtUtils.toInt(asString(), defaultValue);
    }

    @Override
    public long asLong(long defaultValue) {
        return CvtUtils.toLong(asString(), defaultValue);
    }

    @Override
    public long asSpace() {
        return CvtUtils.abbreviatedToLong(asString());
    }

    @Override
    public long asDuration() {
        return CvtUtils.abbreviatedTimeToLong(asString());
    }

    @Override
    public boolean asBool() {
        return asBool(false);
    }
    
    @Override
    public boolean asBool(boolean defaultValue) {
        return CvtUtils.toBool(asString(), defaultValue);
    }
    
    @Override
    public Set<String> split() {
        return split(SPACE);
    }

    @Override
    public Set<String> split(String pattern) {
        return values.stream()
                .flatMap((s) -> Utils.split(s, pattern).stream())
                .map(s -> s.trim())
                .filter(s -> !Utils.isEmpty(s))
                .collect(Collectors.toSet());
    }
}
