package com.iteaj.util.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface NodeWrapper<T> extends JsonWrapper<T> {

    /**
     * 返回节点Key
     * @return
     */
    String getKey();

    /**
     * 返回此节点对应的val
     * @return
     */
    Object getVal();

    /**
     * 创建一个节点, 并且成为此节点的子节点
     * @param key
     * @param val
     * @return
     */
    JsonWrapper asChild(String key, Object val);
}
