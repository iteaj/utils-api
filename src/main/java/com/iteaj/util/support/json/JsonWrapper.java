package com.iteaj.util.support.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface JsonWrapper {

    /**
     * 返回一个适配器
     * @return
     */
    JsonAdapter getAdapter();

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
     * 往json里面新增一个Json节点
     * @param nodeWrapper
     * @return
     */
    JsonWrapper put(NodeWrapper nodeWrapper);

    /**
     * 返回一个json字符串
     * @return
     */
    String toJsonString();
}
