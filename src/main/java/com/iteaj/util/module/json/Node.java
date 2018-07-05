package com.iteaj.util.module.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface Node<T> extends Json<T> {

    /**
     * 返回节点Key
     * @return
     */
    String getKey();

    /**
     * 返回此节点对应的val
     * @return
     */
    <T> T getVal();

    /**
     * 返回字符串形式的值
     * @return
     */
    String getValString();
}
