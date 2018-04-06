package com.iteaj.util.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.http.adapter.JdkHttpAdapter;
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
    private JsonNode value;

    protected JacksonNode(String key, JsonNode value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public JsonNode getVal() {
        return this.value;
    }

    @Override
    public String getString() {
        try {
            return JacksonAdapter.objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UtilsException("Json-解析失败", e, UtilsType.JSON);
        }
    }

    @Override
    public JsonWrapper addNode(String key, Object val) {
        if(value instanceof ObjectNode) {
            if(val instanceof JsonWrapper) {
                ((ObjectNode) value).putObject(key)
                        .putPOJO(((JacksonNode) val).key, ((JacksonNode) val).value);
            } else {
                ((ObjectNode) value).putPOJO(key, val);
            }
        } else {
            throw new UtilsException("不是Json节点(不能新增子节点)："+this.key, UtilsType.JSON);
        }
        return this;
    }

    @Override
    public Class<JsonNode> original() {
        return JsonNode.class;
    }

    @Override
    public NodeWrapper getNode(String key) {
        if(value instanceof ObjectNode) {
            JsonNode node = value.get(key);
            if(node == null) return null;
            else return new JacksonNode(key, node);
        }

        return null;
    }

    @Override
    public String toJsonString() {
        try {
            if(value instanceof ObjectNode)
                return JacksonAdapter.objectMapper.writeValueAsString(value);

            ObjectNode objectNode = JacksonAdapter.getNodeFactory().objectNode();
            objectNode.set(key, value);

            return JacksonAdapter.objectMapper.writeValueAsString(objectNode);
        } catch (JsonProcessingException e) {
            throw new UtilsException("Json-解析失败", e, UtilsType.JSON);
        }
    }
}
