package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class FastjsonWrapper extends JSONObject implements JsonWrapper<JSONObject> {


    public FastjsonWrapper() {

    }

    @Override
    public Class<JSONObject> original() {
        return JSONObject.class;
    }

    @Override
    public NodeWrapper getNode(String key) {
        Object o = get(key);
        if(null == o) return null;

        return new FastjsonNode(key, o);
    }


    @Override
    public JsonWrapper addNode(String key, Object val) {
        if(val instanceof FastjsonNode){
            JSONObject object = new JSONObject();
            object.put(((FastjsonNode) val).getKey(), ((FastjsonNode) val).getVal());
            put(key, object);
        } else {
            put(key, val);
        }

        return this;
    }

    @Override
    public String toJsonString() {
        return this.toJSONString();
    }
}
