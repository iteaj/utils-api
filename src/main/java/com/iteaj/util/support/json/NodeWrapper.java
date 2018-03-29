package com.iteaj.util.support.json;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public interface NodeWrapper {

    /**
     * 返回此节点对应的key
     * @return
     */
    String getKey();

    /**
     * 返回此节点对应的val
     * @return
     */
    NodeWrapper getVal();
}
