package com.usebilbo.bootstrap.property.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import com.google.common.annotations.VisibleForTesting;
import com.usebilbo.bootstrap.property.GroupBuilder;

@Singleton
public class GroupBuilderImpl implements GroupBuilder {

    @Override
    public Map<String, Map<String, List<String>>> buildGroups(Map<String, List<String>> properties) {
        Map<String, Map<String, List<String>>> result = new HashMap<>();
        properties.entrySet().forEach(e -> processEntry(e.getKey(), e.getValue(), result));
        
        return result;
    }

    private void processEntry(String key, List<String> value, Map<String, Map<String, List<String>>> result) {
        abbreviations(key).forEach((abbr) -> result.computeIfAbsent(abbr, (k) -> new HashMap<>()).put(key, value));
    }

    @VisibleForTesting
    protected static List<String> abbreviations(String name) {
        List<String> result = new ArrayList<>();
        result.add("*");
        String[] elements = name.split("\\.");
        StringBuilder partial = new StringBuilder();
        for (int i = 0; i < elements.length - 1; i++) {
            if (partial.length() != 0) {
                partial.append('.');
            }
            partial.append(elements[i]);
            result.add(partial.toString() + ".*");
        }
        return result;
    }
}
