package com.iteaj.util.support.json;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

/**
 * Create Date By 2018-03-22
 *
 * @author iteaj
 * @since 1.7
 */
public class SimpleJson implements JsonAdapter<Void> {

    @Override
    public String toJson(Object obj) {
        return null;
    }

    @Override
    public String toJson(Object obj, DateFormat format) {
        return null;
    }

    @Override
    public <T> T toBean(String json, Class<T> clazz) {
        return null;
    }

    @Override
    public <T> List<T> toList(String json, Class<T> elementType) {
        return null;
    }

    @Override
    public <T> T[] toArray(String json, Class<T> elementType) {
        return null;
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
        return null;
    }

    @Override
    public Void getOrigin() {
        throw new UnsupportedOperationException("不支持此操作");
    }
}
