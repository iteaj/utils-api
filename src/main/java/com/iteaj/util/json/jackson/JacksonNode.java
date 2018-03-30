package com.iteaj.util.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JacksonNode implements NodeWrapper<JsonNode> {

    private String key;
    private JsonWrapper wrapper;

    public JacksonNode(String key, JsonNode node) {
        this.key = key;
        wrapper = new JacksonWrapper(node);
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public Object getVal() {
        return null;
    }

    @Override
    public JsonWrapper asChild(String key, Object val) {
        return null;
    }

    @Override
    public NodeWrapper get(String key) {
        return wrapper.get(key);
    }

    @Override
    public JsonWrapper put(String key, Object val) {
        return wrapper.put(key, val);
    }

    @Override
    public JsonWrapper put(NodeWrapper node) {
        return put(node.getKey(), node.getVal());
    }

    @Override
    public Class<JsonNode> original() {
        return JsonNode.class;
    }

    @Override
    public String toJsonString() {
        return wrapper.toJsonString();
    }
}
