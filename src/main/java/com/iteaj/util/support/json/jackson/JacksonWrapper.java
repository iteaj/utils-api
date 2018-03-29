package com.iteaj.util.support.json.jackson;

import com.fasterxml.jackson.databind.JsonNode;
import com.iteaj.util.support.json.JsonAdapter;
import com.iteaj.util.support.json.NodeWrapper;
import com.iteaj.util.support.json.JsonWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JacksonWrapper implements JsonWrapper {

    private JsonNode jsonNode;

    public JacksonWrapper(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    @Override
    public JsonAdapter getAdapter() {
        return null;
    }

    @Override
    public NodeWrapper get(String key) {
        return null;
    }

    @Override
    public JsonWrapper put(String key, Object val) {
        return null;
    }

    @Override
    public JsonWrapper put(NodeWrapper nodeWrapper) {
        return null;
    }

    @Override
    public String toJsonString() {
        return null;
    }
}
