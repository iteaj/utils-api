package com.iteaj.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.iteaj.util.ClientResponse;

/**
 * Create Date By 2018-03-13
 *
 * @author iteaj
 * @since 1.7
 */
public class JsonResponse extends JSONObject implements ClientResponse<JSONObject> {

    private static String DATA_KEY = "data";
    private static String STATUE_KEY="status";
    private static String MESSAGE_KEY= "message";

    public JsonResponse() {
        this(false, null);
    }

    public JsonResponse(boolean status, String message) {
        super.put(STATUE_KEY, status);
        super.put(MESSAGE_KEY, message);
    }


    @Override
    @JSONField(serialize = false)
    public JSONObject getData() {
        return this;
    }

    @Override
    public Object get(String key) {
        return super.get(key);
    }

    @Override
    public ClientResponse<JSONObject> add(Object val) {
        return this.add(DATA_KEY, val);
    }

    @Override
    public ClientResponse<JSONObject> add(String key, Object val) {
        super.put(key, val);
        return this;
    }

    @Override
    public ClientResponse<JSONObject> remove(String key) {
        super.remove(key);
        return this;
    }

    @Override
    public boolean contain(String key) {
        return super.containsKey(key);
    }

    @Override
    public boolean getStatus() {
        Object status = super.get(STATUE_KEY);

        return null == status? false : (boolean)status;
    }

    @Override
    public ClientResponse<JSONObject> setStatus(boolean success) {
        super.put(STATUE_KEY, success);
        return this;
    }

    @Override
    public String getMessage() {
        Object message = super.get(MESSAGE_KEY);
        return message == null? null : (String)message;
    }

    @Override
    public ClientResponse<JSONObject> setMessage(String message) {
        super.put(MESSAGE_KEY, message);
        return this;
    }
}
