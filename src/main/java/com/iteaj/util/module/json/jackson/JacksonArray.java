package com.iteaj.util.module.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.iteaj.util.AssertUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonArray;

import java.util.ArrayList;

/**
 * create time: 2018/9/20
 *
 * @author iteaj
 * @since 1.0
 */
public class JacksonArray implements JsonArray {

    private ArrayNode nodes;
    private ObjectMapper mapper;

    public JacksonArray(ArrayNode nodes, ObjectMapper mapper) {
        this.nodes = nodes;
        this.mapper = mapper;
        AssertUtils.isTrue(null != mapper
                , "创建JacksonArray失败, 缺失参数：ObjectMapper对象", UtilsType.JSON);
        AssertUtils.isTrue(null != nodes,
                "创建JacksonArray失败, 缺失参数：ArrayNode对象", UtilsType.JSON);
    }

    @Override
    public ArrayNode original() {
        return this.nodes;
    }

    @Override
    public int size() {
        return original().size();
    }

    @Override
    public short getShort(int index, boolean... isThrow) {
        JsonNode node = original().get(index);
        isThrow(!node.isShort(), index, isThrow);

        return node == null ? 0 : node.isShort() ? node.shortValue() : 0;
    }

    @Override
    public int getInt(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isInt(), index, isThrow);
        return node == null ? 0 : !node.isInt()
                ? 0 : node.intValue();
    }

    @Override
    public float getFloat(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isFloat(), index, isThrow);
        return node == null ? 0 : !node.isFloat()
                ? 0 : node.floatValue();
    }

    @Override
    public double getDouble(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isDouble(), index, isThrow);
        return node == null ? 0 : !node.isDouble()
                ? 0 : node.doubleValue();
    }

    @Override
    public long getLong(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isLong(), index, isThrow);
        return node == null ? 0 : !node.isLong()
                ? 0 : node.longValue();
    }

    @Override
    public boolean getBoolean(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isBoolean(), index, isThrow);
        return node == null ? false : !node.isBoolean()
                ? false : node.asBoolean();
    }

    @Override
    public String getString(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(node == null, index, isThrow);
        return node == null ? null : node.asText(null);
    }

    @Override
    public Json getJson(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isObject(), index, isThrow);
        node = node == null || !node.isObject() ? mapper.createObjectNode() : node;
        return new Jackson((ObjectNode) node, mapper);
    }

    @Override
    public JsonArray getJsonArray(int index, boolean... isThrow) {
        JsonNode node = this.original().get(index);
        isThrow(!node.isArray(), index, isThrow);
        node = node == null || !node.isArray() ? mapper.createArrayNode() : node;
        return new JacksonArray((ArrayNode) node, mapper);
    }

    @Override
    public <T> ArrayList<T> toList(Class<T> clazz) {
        CollectionType type = mapper.getTypeFactory()
                .constructCollectionType(ArrayList.class, clazz);

        return (ArrayList<T>) mapper.convertValue(original(), type);
    }

    @Override
    public <T> T toBean(Class<T> arrayType) {
        try {
            if(!arrayType.isArray())
                throw new UtilsException("反序列化JsonArray, 参数类型必须是数组类型eg：Integer[].class", UtilsType.JSON);

            return mapper.treeToValue(original(), arrayType);
        } catch (JsonProcessingException e) {
            throw new UtilsException("反序列化JsonArray到Java对象失败", e, UtilsType.JSON);
        }
    }
}
