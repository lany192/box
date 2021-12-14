package com.github.lany192.arch.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapBuilder {
    private Map<String, Object> map = new LinkedHashMap<>();

    private MapBuilder() {

    }

    public static MapBuilder of() {
        return new MapBuilder();
    }

    public MapBuilder put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public MapBuilder putAll(Map<String, Object> hashMap) {
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            this.map.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Map<String, Object> build() {
        return map;
    }
}
