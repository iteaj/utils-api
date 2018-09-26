package com.iteaj.util.module.json.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iteaj.util.AssertUtils;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.AbstractJson;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonArray;
import com.iteaj.util.module.json.Type;

import java.io.IOException;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class Jackson extends AbstractJson<JsonNode> {

    private JsonNode node;
    private ObjectMapper mapper;

    public Jackson(String json, ObjectMapper mapper) {
        try {
            this.mapper = mapper;
            AssertUtils.isTrue(mapper != null
                    , "实例化Jackson对象必须传入ObjectMapper对象作为构造参数", UtilsType.JSON);

            this.node = mapper.readTree(json);
            AssertUtils.isTrue(this.node.isContainerNode(), "错误的json字符串："+json, UtilsType.JSON);
        } catch (IOException e) {
            throw new UtilsException("构建Jackson失败, 错误的json字符串："+json, UtilsType.JSON);
        }
    }

    public Jackson(ContainerNode node, ObjectMapper mapper) {
        this.node = node;
        this.mapper = mapper;
        AssertUtils.isTrue(mapper != null
                , "实例化Jackson对象必须传入ObjectMapper对象作为构造参数", UtilsType.JSON);
    }

    @Override
    public Type getType() {
        return Type.Jackson;
    }

    @Override
    public JsonNode original() {
        return this.node;
    }

    @Override
    public int size() {
        return original().size();
    }

    @Override
    public Json add(String name, Object val) {
        if(CommonUtils.isBlank(name))
            throw new UtilsException("新增Json节点时必须指定节点名", UtilsType.JSON);

        if(original() instanceof ObjectNode) {
            if(val instanceof Jackson) {
                ((ObjectNode) original()).set(name, ((Jackson) val).original());
            } else if(val instanceof JacksonArray) {
                ((ObjectNode) original()).set(name, ((JacksonArray) val).original());
            } else {
                ((ObjectNode) original()).putPOJO(name, val);
            }
        } else {
            throw new UtilsException("只允许ObjectNode实例新增节点", UtilsType.JSON);
        }

        return this;
    }

    @Override
    public Json remove(String name) {
        if(original() instanceof ObjectNode) {
            ((ObjectNode) original()).remove(name);
        } else {
            logger.warn("类别：Json - 动作：移除Json节点 - 信息：只允许ObjectNode实例进行节点移除");
        }

        return this;
    }

    @Override
    public boolean isExist(String name) {
        return original().get(name) != null;
    }

    @Override
    public boolean isNull(String name, boolean... isThrow) {
        JsonNode jsonNode = original().get(name);
        boolean isThrowed = CommonUtils.isNotEmpty(isThrow) ? isThrow[0] : false;

        if(jsonNode == null && isThrowed)
            throw new UtilsException("Json对象不存在节点：" + name, UtilsType.JSON);

        return null == jsonNode ? true : jsonNode.isNull();
    }

    @Override
    public String toJsonString() {
        try {
            return this.mapper.writeValueAsString(this.original());
        } catch (JsonProcessingException e) {
            throw new UtilsException("写出Json字符串失败", e, UtilsType.JSON);
        }
    }

    @Override
    public boolean getBoolean(String name, boolean... defaultAndThrow) {
        JsonNode node = this.original().get(name);
        boolean defaultValue = false, isThrow = false;
        if(CommonUtils.isNotEmpty(defaultAndThrow)) {
            defaultValue = defaultAndThrow[0];
            isThrow = defaultAndThrow.length != 2 ? false : defaultAndThrow[1];
        }

        if((node == null || !node.isBoolean()) && isThrow)
            throw new UtilsException("Json节点缺失或类型不匹配" +
                    ", 如果您希望不抛异常则请指定参数isThrow=false", UtilsType.JSON);

        return node == null ? defaultValue : !node.isBoolean()
                ? defaultValue : node.asBoolean(defaultValue);
    }

    @Override
    public short getShort(String name, short defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null || !node.isShort(), isThrow);
        return node == null ? defaultValue : !node.isShort()
                ? defaultValue : node.shortValue();
    }

    @Override
    public int getInt(String name, int defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null || !node.isInt(), isThrow);
        return node == null ? defaultValue : !node.isInt()
                ? defaultValue : node.intValue();
    }

    @Override
    public long getLong(String name, long defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null || !node.isLong(), isThrow);
        return node == null ? defaultValue : !node.isLong()
                ? defaultValue : node.longValue();
    }

    @Override
    public float getFloat(String name, float defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null || !node.isFloat(), isThrow);
        return node == null ? defaultValue : !node.isFloat()
                ? defaultValue : node.floatValue();
    }

    @Override
    public double getDouble(String name, double defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null || !node.isDouble(), isThrow);
        return node == null ? defaultValue : !node.isDouble()
                ? defaultValue : node.doubleValue();
    }

    @Override
    public String getString(String name, String defaultValue, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow(node == null, isThrow);
        return node == null ? defaultValue : node.asText(defaultValue);
    }

    @Override
    public <R> R toBean(Class<R> clazz) {
        try {
            return mapper.treeToValue(original(), clazz);
        } catch (JsonProcessingException e) {
            throw new UtilsException("Json转对象失败："+original().toString()
                    +" --> "+clazz.getName(), e, UtilsType.JSON);
        }
    }

    @Override
    public JsonArray toJsonArray(boolean... isThrow) {
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(!original().isArray(), isThrow);

        if(original().isArray())
            return new JacksonArray((ArrayNode) original(), mapper);

        return new JacksonArray(mapper.getNodeFactory().arrayNode(), mapper);
    }

    @Override
    public JsonArray getJsonArray(String name, boolean... isThrow) {
        JsonNode node = original().get(name);
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(node == null || !node.isArray(), isThrow);

        node = node == null || !node.isArray()
                ? mapper.getNodeFactory().arrayNode() : node;

        return new JacksonArray((ArrayNode) node, mapper);
    }

    @Override
    public Json getJson(String name, boolean... isThrow) {
        JsonNode node = this.original().get(name);
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(node == null || !node.isObject(), isThrow);

        node = node == null || !node.isObject()
                ? mapper.getNodeFactory().objectNode() : node;

        return new Jackson((ObjectNode) node, mapper);
    }

    @Override
    public <T> T getPath(String path, boolean... isThrow) {
        JsonNode at = this.original().at(path);
        isThrow(at.isMissingNode(), isThrow);

        if(at.isMissingNode()) return null;
        else if(at.isShort()) return (T) Short.valueOf(at.shortValue());
        else if(at.isInt()) return (T) Integer.valueOf(at.intValue());
        else if(at.isLong()) return (T) Long.valueOf(at.longValue());
        else if(at.isFloat()) return (T) Float.valueOf(at.floatValue());
        else if(at.isDouble()) return (T) Double.valueOf(at.doubleValue());
        else if(at.isBoolean()) return (T) Boolean.valueOf(at.booleanValue());
        else if(at.isContainerNode()) return (T) new Jackson((ContainerNode) at, mapper);
        else return null;
    }
}
