package com.iteaj.util.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class FastjsonNode implements NodeWrapper<JSON> {

    private String key;
    private Object value;

    public FastjsonNode(String key, Object val) {
        this.key = key;
        this.value = val;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public Object getVal() {
        return value;
    }

    @Override
    public Class<JSON> original() {
        return JSON.class;
    }

    @Override
    public NodeWrapper get(String key) {
        if(value instanceof JSONObject) {
            return new FastjsonNode(key, ((JSONObject) value).get(key));
        }

        return new FastjsonNode(key, value);
    }

    @Override
    public JsonWrapper put(String key, Object val) {
        if(value instanceof JSONObject) {
            ((JSONObject) value).put(key, val);
            return this;
        }

        throw new IllegalStateException("此节点不是Json节点：" + value);
    }

    @Override
    public JsonWrapper asChild(String key, Object val) {
        if(value instanceof JSONObject) {
            ((JSONObject) value).put(key, JSON.toJSON(val));
            return this;
        }

        throw new IllegalStateException("此节点不是Json节点：" + value);
    }

    @Override
    public JsonWrapper put(NodeWrapper node) {
        return put(node.getKey(), node.getVal());
    }

    @Override
    public String toJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject.toJSONString();
    }
}
