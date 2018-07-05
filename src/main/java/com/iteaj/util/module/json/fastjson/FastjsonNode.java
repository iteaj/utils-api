package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.Node;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class FastjsonNode implements Node<JSONObject> {

    private String key;
    private Object value;

    protected FastjsonNode(String key, Object val) {
        this.key = key;
        this.value = val;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public <T> T getVal() {
        return (T)value;
    }

    @Override
    public String getValString() {
        if(value instanceof JSON)
            return ((JSON) value).toJSONString();
        else if(value instanceof Number)
            return value.toString();
        else if(value instanceof Enum)
            return value.toString();
        else if(value instanceof Boolean)
            return value.toString();
        else return JSON.toJSON(value).toString();
    }

    @Override
    public Class<JSONObject> original() {
        return JSONObject.class;
    }

    @Override
    public Node getNode(String key) {
        if(!CommonUtils.isNotBlank(key))
            throw new UtilsException("Json-节点Key必须存在", UtilsType.JSON);

        if(value instanceof JSONObject) {
            Object o = ((JSONObject) value).get(key);
            if(null == o) return null;
            return new FastjsonNode(key, o);
        }

        return null;
    }

    @Override
    public Json addNode(String key, Object val) {
        if(!CommonUtils.isNotBlank(key))
            throw new UtilsException("Json-节点Key必须存在", UtilsType.JSON);

        if(value instanceof JSONObject) {
            if(val instanceof Fastjson) {
                ((JSONObject) value).put(key, val);
            } else if(val instanceof FastjsonNode) {
                JSONObject object = new JSONObject();
                object.put(((FastjsonNode) val).getKey(), ((FastjsonNode) val).getVal());
                ((JSONObject) value).put(key, object);
            } else  {
                ((JSONObject) value).put(key, val);
            }

            return this;
        }

        throw new UtilsException("不是Json节点(不能新增子节点)："+this.key, UtilsType.JSON);
    }

    @Override
    public String toJsonString() {
        if(value instanceof JSON)
            return ((JSON) value).toJSONString();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(key, value);
        return jsonObject.toJSONString();
    }
}
