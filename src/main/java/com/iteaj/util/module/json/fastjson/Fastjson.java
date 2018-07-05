package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.Node;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class Fastjson extends JSONObject implements Json<JSONObject> {


    public Fastjson() {

    }

    @Override
    public Class<JSONObject> original() {
        return JSONObject.class;
    }

    @Override
    public Node getNode(String key) {
        Object o = get(key);
        if(null == o) return null;

        return new FastjsonNode(key, o);
    }


    @Override
    public Json addNode(String key, Object val) {
        if(val instanceof Fastjson) {
            this.put(key, val);
        } else if(val instanceof FastjsonNode) {
            JSONObject object = new JSONObject();
            object.put(((FastjsonNode) val).getKey(), ((FastjsonNode) val).getVal());
            this.put(key, object);
        } else  {
            this.put(key, val);
        }
        return this;
    }

    @Override
    public String toJsonString() {
        return this.toJSONString();
    }
}
