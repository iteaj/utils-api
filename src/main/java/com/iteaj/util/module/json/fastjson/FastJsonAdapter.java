package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonAdapter;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Create Date By 2018-02-26
 *
 * @author iteaj
 * @since 1.7
 */
public class FastJsonAdapter implements JsonAdapter<JSON> {

    @Override
    public String toJson(Object obj) {
        if(null == obj) return null;
        return JSON.toJSONString(obj);
    }

    @Override
    public String toJson(Object obj, SimpleDateFormat format) {
        if(null == obj) return null;
        if(null == format) return JSON.toJSONString(obj);
        return JSON.toJSONStringWithDateFormat(obj, format.toPattern());
    }

    @Override
    public <T> T toBean(String json, Class<T> clazz) {
        if(null == json || clazz == null) return null;
        return JSON.parseObject(json, clazz);
    }

    @Override
    public <T> List<T> toList(String json, Class<T> elementType) {
        if(null == json || elementType == null) return null;
        return JSONObject.parseArray(json, elementType);
    }

    @Override
    public <T> T toArray(String json, Class<T> arrayType) {
        if(!arrayType.isArray())
            throw new UtilsException("反序列化JsonArray, 参数类型必须是数组类型eg：Integer[].class", UtilsType.JSON);

        return JSONObject.parseObject(json, arrayType);
    }

    @Override
    public <K, V> Map<K, V> toMap(String json, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
        if(null == json) return null;
        if(null == mapType || null == keyType || valueType == null)
            throw new UtilsException("Json解析到Map 必须指定Map的Key-Value类型", UtilsType.JSON);

        if(mapType == HashMap.class)
            return JSON.parseObject(json, new TypeReference<HashMap<K, V>>() {});
        else if(mapType == TreeMap.class)
            return JSON.parseObject(json, new TypeReference<TreeMap<K, V>>(){});
        else if(mapType == LinkedHashMap.class)
            return JSON.parseObject(json, new TypeReference<LinkedHashMap<K, V>>(){});
        else return JSON.parseObject(json, new TypeReference<HashMap<K, V>>() {});
    }

    @Override
    public JSON getOriginal() {
        return new JSONObject();
    }

    @Override
    public Json builder() {
        return new Fastjson(new JSONObject());
    }

    @Override
    public Json builder(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        return new Fastjson(jsonObject);
    }
}
