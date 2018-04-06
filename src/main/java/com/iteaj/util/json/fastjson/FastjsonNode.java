package com.iteaj.util.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class FastjsonNode implements NodeWrapper<JSONObject> {

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
    public Object getVal() {
        return value;
    }

    @Override
    public String getString() {
        if(value instanceof JSON)
            return ((JSON) value).toJSONString();

        return value.toString();
    }

    @Override
    public Class<JSONObject> original() {
        return JSONObject.class;
    }

    @Override
    public NodeWrapper getNode(String key) {
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
    public JsonWrapper addNode(String key, Object val) {
        if(!CommonUtils.isNotBlank(key))
            throw new UtilsException("Json-节点Key必须存在", UtilsType.JSON);

        if(value instanceof JSONObject) {
            if(val instanceof FastjsonWrapper) {
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
