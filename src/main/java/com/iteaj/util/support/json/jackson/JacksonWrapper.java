package com.iteaj.util.support.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iteaj.util.support.json.JsonWrapper;
import com.iteaj.util.support.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JacksonWrapper implements JsonWrapper<JsonNode> {

    private JsonNode jsonNode;

    public JacksonWrapper(JsonNode jsonNode) {
        this.jsonNode = jsonNode;
    }

    @Override
    public NodeWrapper get(String key) {
        JsonNode jsonNode = this.jsonNode.get(key);
        return new JacksonNode(key, jsonNode);
    }

    @Override
    public JsonWrapper put(String key, Object val) {
        ObjectNode jsonNode = (ObjectNode) this.jsonNode;
        if(val instanceof JsonWrapper) {
            jsonNode.putPOJO(key
                    , ((JsonWrapper) val).original());
        }
        else {

//            jsonNode.putPOJO(key, jsonNode.);
        }
        return this;
    }

    @Override
    public JsonWrapper put(NodeWrapper node) {
        return this.put(node.getKey(), node.getVal());
    }

    @Override
    public Class<JsonNode> original() {
        return JsonNode.class;
    }

    @Override
    public String toJsonString() {
        try {
            return JacksonAdapter.objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
