package com.iteaj.util.module.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.Node;

import java.util.Map;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class Jackson extends ObjectNode implements Json<JsonNode> {

    protected Jackson() {
        super(JacksonAdapter.getNodeFactory());
    }

    protected Jackson(JsonNodeFactory nc) {
        super(nc);
    }

    protected Jackson(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }

    @Override
    public Json addNode(String key, Object val) {
        if(val instanceof JacksonNode) {
            ObjectNode node = this.putObject(key);
            node.set(((JacksonNode) val).getKey()
                    , ((JacksonNode) val).<JsonNode>getVal());
        } else if(val instanceof Jackson) {
            this.set(key, (ObjectNode)val);
        } else {
            this.putPOJO(key, val);
        }

        return this;
    }

    @Override
    public Class<JsonNode> original() {
        return JsonNode.class;
    }

    @Override
    public Node getNode(String key) {
        JsonNode node = this.get(key);
        if(null == node) return null;
        return new JacksonNode(key, node);
    }

    @Override
    public String toJsonString() {
        try {
            return JacksonAdapter.objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
