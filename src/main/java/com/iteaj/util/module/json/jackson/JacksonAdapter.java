package com.iteaj.util.module.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.JsonAdapter;
import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.json.NodeWrapper;

import java.io.IOException;
import java.text.DateFormat;
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
            if(null != format)
                return objectMapper.writer(format).writeValueAsString(obj);

            return objectMapper.writeValueAsString(obj);
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
     * @param elementType   数组的类型
     * @return
     */
    public Object[] toArray(String json, Class elementType){
        try {
            if(null == json || null == elementType) return null;
            ArrayType arrayType = getTypeFactory(objectMapper).constructArrayType(elementType);
            return objectMapper.readValue(json, arrayType);
        } catch (IOException e) {
            throw new UtilsException(e.getMessage(), e, UtilsType.JSON);
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
    public JsonWrapper build() {
        return new JacksonWrapper();
    }

    @Override
    public JsonWrapper build(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json);

            JacksonWrapper jsonNodes = new JacksonWrapper();
            jsonNodes.setAll((ObjectNode) jsonNode);

            return jsonNodes;
        } catch (Exception e) {
            throw new UtilsException("Json - 错误的json字符串："+json, UtilsType.JSON);
        }
    }

    @Override
    public NodeWrapper buildNode(String name, Object val) {
        return new JacksonNode(name, getNodeFactory().pojoNode(val));
    }

    public static JsonNodeFactory getNodeFactory() {
        return objectMapper.getNodeFactory();
    }

    public static TypeFactory getTypeFactory(ObjectMapper objectMapper){
        return objectMapper.getTypeFactory();
    }

}
