package com.iteaj.util.module.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create Date By 2016/9/26
 *  Jackson工具类  线程安全
 * @author iteaj
 * @since 1.7
 */
public class JacksonAdapter implements JsonAdapter<ObjectMapper> {

    protected ObjectMapper objectMapper;

    public JacksonAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        AssertUtils.isTrue(objectMapper != null
                , "实例化JacksonAdapter必须传入ObjectMapper对象作为构造参数", UtilsType.JSON);
    }

    /**
     * java对象转json
     * @param obj   java对象
     * @return
     */
    public String toJson(Object obj){
        try {
            if(null == obj) return null;
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
        }
    }

    /**
     * java对象转json
     * @param obj   java对象
     * @param format    日期格式化
     * @return
     */
    public String toJson(Object obj, SimpleDateFormat format){
        try {
            if(null == obj) return null;
            if(null == format)
                throw new UtilsException("写出Json字符串时必须指定时间格式对象：SimpleDateFormat", UtilsType.JSON);

            return objectMapper.writer(format).writeValueAsString(obj);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
        }
    }

    /**
     * json转pojo对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T toBean(String json, Class<T> clazz){
        try {
            if(null == json || null == clazz) return null;
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
        }
    }

    /**
     * json转List对象
     * @param json  json字符串
     * @param elementType   集合元素类型 eg: OrderBy.class
     * @param <T>
     * @return
     */
    public <T> List<T> toList(String json, Class<T> elementType){
        try {
            if(null == json || null == elementType) return null;
            CollectionType type = getTypeFactory(objectMapper)
                    .constructCollectionType(ArrayList.class, elementType);

            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
        }

    }

    /**
     * json转数组对象
     * @param json
     * @param arrayType   数组的类型
     * @return
     */
    public <T> T toArray(String json, Class<T> arrayType){
        if(!arrayType.isArray())
            throw new UtilsException("反序列化JsonArray, 参数类型必须是数组类型eg：Integer[].class", UtilsType.JSON);
        return toBean(json, arrayType);
    }

    /**
     * json转成map
     * @param json
     * @param mapType   eg: HashMap.class
     * @param keyType   eg: String.class
     * @param valueType eg: OrderBy.class
     * @param <K>
     * @param <V>
     * @return
     */
    public <K,V> Map<K,V> toMap(String json, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType){
        try {
            if(null == json) return null;
            if(null == mapType || null == keyType || valueType == null)
                throw new UtilsException("Json解析到Map 必须指定Map的Key-Value类型", UtilsType.JSON);

            MapType mapType1 = getTypeFactory(objectMapper)
                    .constructMapType(mapType, keyType, valueType);

            return objectMapper.readValue(json, mapType1);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
        }
    }

    @Override
    public ObjectMapper getOriginal() {
        return objectMapper;
    }

    @Override
    public Json builder() {
        return new Jackson(getNodeFactory().objectNode(), objectMapper);
    }

    @Override
    public Json builder(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            if(jsonNode.isContainerNode())
                return new Jackson((ContainerNode) jsonNode, objectMapper);

            throw new UtilsException("错误的json字符串："+json, UtilsType.JSON);
        } catch (IOException e) {
            throw new UtilsException("错误的json字符串："+json, UtilsType.JSON);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected JsonNodeFactory getNodeFactory() {
        return getObjectMapper().getNodeFactory();
    }

    protected TypeFactory getTypeFactory(ObjectMapper objectMapper){
        return objectMapper.getTypeFactory();
    }

}
