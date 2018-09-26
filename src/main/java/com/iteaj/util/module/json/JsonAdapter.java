package com.iteaj.util.module.json;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Create Date By 2018-02-26
 *  Json适配器, 用来适配FastJson 或者 Jackson or Gson
 * @author iteaj
 * @since 1.7
 */
public interface JsonAdapter<R> {

    /**
     * java对象转json
     * @param obj   java对象
     * @return
     */
    String toJson(Object obj);

    /**
     * java对象转json
     * @param obj   java对象
     * @param format    日期格式化
     * @return
     */
    String toJson(Object obj, SimpleDateFormat format);

    /**
     * json转pojo对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T toBean(String json, Class<T> clazz);

    /**
     * json转List对象
     * @param json  json字符串
     * @param elementType   集合元素类型 eg: OrderBy.class
     * @param <T>
     * @return
     */
    <T> List<T> toList(String json
            , Class<T> elementType);

    /**
     * json转数组对象
     * @param json
     * @param arrayType   数组的类型
     * @return
     */
    <T> T toArray(String json, Class<T> arrayType);

    /**
     * json转成map
     * @param json
     * @param mapType   e.g: HashMap.class
     * @param keyType   e.g: String.class
     * @param valueType e.g: OrderBy.class
     * @param <K>
     * @param <V>
     * @return
     */
    <K,V> Map<K,V> toMap(String json, Class<? extends Map> mapType
            , Class<K> keyType, Class<V> valueType);

    /**
     * 返回每种适配器的原始对象
     * eg.. {@code FastJson的JSONObject} {@code Jackson的ObjectMapper}
     * @return
     */
    R getOriginal();

    /**
     * 构建一个json对象
     * @return
     */
    Json builder();

    /**
     * 通过json字符串构建一个Json对象
     * @param json
     * @return
     */
    Json builder(String json);

}
