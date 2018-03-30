package com.iteaj.util.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface JsonWrapper<T> {

    /**
     * 返回存储Json数据的真正对象
     * @see com.fasterxml.jackson.databind.JsonNode
     * @see com.alibaba.fastjson.JSON
     * @return
     */
    Class<T> original();

    /**
     * 返回json里面指定key的json
     * @param key
     * @return
     */
    NodeWrapper get(String key);

    /**
     * 往json里面新增一个key-val的键值对
     * @param key
     * @param val
     * @return
     */
    JsonWrapper put(String key, Object val);

    /**
     * 往json里面新增一个节点
     * @param node
     * @return
     */
    JsonWrapper put(NodeWrapper node);

    /**
     * 返回一个json字符串
     * @return
     */
    String toJsonString();
}
