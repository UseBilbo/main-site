package com.usebilbo.vertx.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {
    private Map<K, V> result = new HashMap<>();
    
    private MapBuilder() {
    }

    private MapBuilder(Map<K, V> source) {
        result.putAll(source);
    }
    
    public static <K, V> MapBuilder<K, V> create() {
        return new MapBuilder<>();
    }

    public static <K, V> MapBuilder<K, V> create(Map<K, V> source) {
        return new MapBuilder<>(source);
    }
    
    public MapBuilder<K, V> with(K key, V value) {
        result.put(key, value);
        return this;
    }

    public Map<K, V> build() {
        return this.result;
    }
}
