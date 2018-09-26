package com.iteaj.util.module.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface Json<T> {

    /**
     * 返回适配的Json库类型
     * @return
     */
    Type getType();

    /**
     * 返回存储Json数据的真正对象
     * @see com.fasterxml.jackson.databind.JsonNode
     * @see com.alibaba.fastjson.JSON
     * @return
     */
    T original();

    /**
     * 此Json对象有几个节点
     * @return
     */
    int size();

    /**
     * 增加一个节点到Json对象
     * @param name
     * @param val
     * @return
     */
    Json add(String name, Object val);

    /**
     * 移除一个节点
     * @param name
     * @return
     */
    Json remove(String name);

    /**
     * 是否存在节点
     * @param name
     * @return
     */
    boolean isExist(String name);

    /**
     * 节点值是否等于{@code null}
     * @param name
     * @return
     */
    boolean isNull(String name, boolean... isThrow);

    boolean getBoolean(String name, boolean... defaultAndThrow);

    /**
     * @param name
     * @return
     */
    short getShort(String name, boolean... isThrow);

    short getShort(String name, short defaultValue, boolean... isThrow);

    /**
     * 返回一个整数
     * @param name 字段名
     * @return
     */
    int getInt(String name, boolean... isThrow);

    /**
     *
     * @param name
     * @param defaultValue
     * @param isThrow
     * @return
     */
    int getInt(String name, int defaultValue, boolean... isThrow);

    /**
     * 返回一个Long值
     * @param name 字段名
     * @return
     */
    long getLong(String name, boolean... isThrow);

    long getLong(String name, long defaultValue, boolean... isThrow);

    float getFloat(String name, boolean ... isThrow);

    float getFloat(String name, float defaultValue, boolean... isThrow);

    double getDouble(String name, boolean... isThrow);

    double getDouble(String name, double defaultValue, boolean... isThrow);

    String getString(String name, boolean... isThrow);

    String getString(String name, String defaultValue, boolean... isThrow);

    <R> R toBean(Class<R> clazz);

    JsonArray toJsonArray(boolean... isThrow);

    JsonArray getJsonArray(String name, boolean... isThrow);

    Json getJson(String name, boolean... isThrow);

    /**
     * 获取指定路径下面的节点
     * 分隔符：'/' 比如：/name/test
     * @param path
     * @return
     */
    <R> R getPath(String path, boolean... isThrow);

    /**
     * 返回一个json字符串
     * @return
     */
    String toJsonString();
}
