package com.iteaj.util.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.json.JsonAdapter;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

/**
 * Create Date By 2018-02-26
 *
 * @author iteaj
 * @since 1.7
 */
public class FastJsonAdapter implements JsonAdapter<JSON> {

    @Override
    public String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    @Override
    public String toJson(Object obj, DateFormat format) {
        return JSON.toJSONString(obj);
    }

    @Override
    public <T> T toBean(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> List<T> toList(String json, Class<T> elementType) {
        return JSONObject.parseArray(json, elementType);
    }

    @Override
    public <T> T[] toArray(String json, Class<T> elementType) {
//        toList(json, elementType).toArray()
        return null;
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
        return null;
    }

    @Override
    public JSON getOriginal() {
        return new JSONObject();
    }

    @Override
    public JsonWrapper build() {
        return new FastjsonWrapper();
    }

    @Override
    public NodeWrapper build(String name, Object val) {
        return null;
    }
}
