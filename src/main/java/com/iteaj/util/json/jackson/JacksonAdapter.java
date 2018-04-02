package com.iteaj.util.json.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.iteaj.util.json.JsonAdapter;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
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

    protected static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    /**
     * java对象转json
     * @param obj   java对象
     * @return
     */
    public String toJson(Object obj){
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * java对象转json
     * @param obj   java对象
     * @param format    日期格式化
     * @return
     */
    public String toJson(Object obj, DateFormat format){
        try {
            return objectMapper.writer(format).writeValueAsString(obj);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
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
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
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
            CollectionType type = getTypeFactory(objectMapper)
                    .constructCollectionType(ArrayList.class, elementType);

            return objectMapper.readValue(json, type);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

    }

    /**
     * json转数组对象
     * @param json
     * @param elementType   数组的类型
     * @param <T>
     * @return
     */
    public <T> T[] toArray(String json, Class<T> elementType){
        try {
            ArrayType arrayType = getTypeFactory(objectMapper).constructArrayType(elementType);
            return objectMapper.readValue(json, arrayType);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

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
    public <K,V> Map<K,V> toMap(String json, Class<? extends Map<K,V>> mapType, Class<K> keyType, Class<V> valueType){
        try {
            MapType mapType1 = getTypeFactory(objectMapper)
                    .constructMapType(mapType, keyType, valueType);

            return objectMapper.readValue(json, mapType1);
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public ObjectMapper getOriginal() {
        return objectMapper;
    }

    @Override
    public JsonWrapper build() {
        return new JacksonWrapper();
    }

    @Override
    public NodeWrapper build(String name, Object val) {
        return new JacksonNode(name, getNodeFactory().pojoNode(val));
    }

    public static JsonNodeFactory getNodeFactory() {
        return objectMapper.getNodeFactory();
    }

    public static TypeFactory getTypeFactory(ObjectMapper objectMapper){
        return objectMapper.getTypeFactory();
    }

}
