package com.iteaj.util.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class FastjsonWrapper implements JsonWrapper<JSON> {

    private JSONObject jsonObject;

    public FastjsonWrapper() {
        this.jsonObject = new JSONObject();
    }

    public FastjsonWrapper(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public Class<JSON> original() {
        return null;
    }

    @Override
    public NodeWrapper get(String key) {
        Object o = jsonObject.get(key);
        if(null == o) return null;

        return new FastjsonNode(key, o);
    }


    @Override
    public JsonWrapper put(String key, Object val) {
        if(val instanceof NodeWrapper){
            JSONObject object = new JSONObject();
            if(val instanceof JSON) {
                object.put(((NodeWrapper) val).getKey(), ((NodeWrapper) val).getVal());
            } else {
                object.put(((NodeWrapper) val).getKey(), JSON.toJSON(((NodeWrapper) val).getVal()));
            }

            this.jsonObject.put(key, object);
            return this;
        }

        this.jsonObject.put(key, JSON.toJSON(val));
        return this;
    }

    @Override
    public JsonWrapper put(NodeWrapper node) {
        return put(node.getKey(), node.getVal());
    }

    @Override
    public String toJsonString() {
        return jsonObject.toJSONString();
    }
}
