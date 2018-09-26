package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.AbstractJson;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.JsonArray;
import com.iteaj.util.module.json.Type;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class Fastjson extends AbstractJson<JSON> {

    private JSON json;

    public Fastjson(JSON json) {
        this.json = json;
    }

    @Override
    public Type getType() {
        return Type.FastJson;
    }

    @Override
    public JSON original() {
        return this.json;
    }

    @Override
    public int size() {
        return getObject().size();
    }

    @Override
    public Json add(String name, Object val) {
        getObject().put(name, val);
        return this;
    }

    @Override
    public Json remove(String name) {
        getObject().remove(name);
        return this;
    }

    @Override
    public boolean isExist(String name) {
        return getObject().containsKey(name);
    }

    @Override
    public boolean isNull(String name, boolean... isThrow) {
        Object o = getObject().get(name);
        boolean isThrowed = CommonUtils.isNotEmpty(isThrow) ? isThrow[0] : false;

        if(o == null && isThrowed)
            throw new UtilsException("Json对象不存在节点：" + name, UtilsType.JSON);

        return o == null;
    }

    protected JSONObject getObject() {
        if(json instanceof JSONObject)
            return (JSONObject) json;

        throw new UtilsException("只有JSONObject对象才允许的操作", UtilsType.JSON);
    }

    @Override
    public String toJsonString() {
        return original().toJSONString();
    }

    @Override
    public boolean getBoolean(String name, boolean... defaultAndThrow) {
        Object o = getObject().get(name);
        boolean defaultValue = false, isThrow = false;
        if(CommonUtils.isNotEmpty(defaultAndThrow)) {
            defaultValue = defaultAndThrow[0];
            isThrow = defaultAndThrow.length != 2 ? false : defaultAndThrow[1];
        }
        isThrow(o == null || !(o instanceof Boolean), isThrow);
        return o == null ? false : o instanceof Boolean ? (Boolean) o : false;
    }

    @Override
    public short getShort(String name, short defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null || !(o instanceof Short), isThrow);
        return o != null && o instanceof Short ? (Short) o : defaultValue;
    }

    @Override
    public int getInt(String name, int defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null || !(o instanceof Integer), isThrow);
        return o != null && o instanceof Integer ? (Integer) o : defaultValue;
    }

    @Override
    public long getLong(String name, long defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null || !(o instanceof Long), isThrow);
        return o != null && o instanceof Integer ? (Long) o : defaultValue;
    }

    @Override
    public float getFloat(String name, float defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null || !(o instanceof Float), isThrow);
        return o != null && o instanceof Float ? (Float) o : defaultValue;
    }

    @Override
    public double getDouble(String name, double defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null || !(o instanceof Double), isThrow);
        return o != null && o instanceof Double ? (Double) o : defaultValue;
    }

    @Override
    public String getString(String name, String defaultValue, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow(o == null, isThrow);
        return o != null ? o.toString() : defaultValue;
    }


    @Override
    public <R> R toBean(Class<R> clazz) {
        return original().toJavaObject(clazz);
    }

    @Override
    public JsonArray toJsonArray(boolean... isThrow) {
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(!(original() instanceof JSONArray), isThrow);
        return original() instanceof JSONArray ?
                new FastJsonArray((JSONArray) original())
                : new FastJsonArray(new JSONArray());
    }

    @Override
    public JsonArray getJsonArray(String name, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(o == null || !(o instanceof JSONArray), isThrow);
        return o == null ? null : o instanceof JSONArray
                ? new FastJsonArray((JSONArray) o)
                : new FastJsonArray(new JSONArray());
    }

    @Override
    public Json getJson(String name, boolean... isThrow) {
        Object o = getObject().get(name);
        isThrow = CommonUtils.isNotEmpty(isThrow) ? isThrow : new boolean[]{true};
        isThrow(o == null || !(o instanceof JSONObject), isThrow);
        return o != null && o instanceof JSONObject
                ? new Fastjson((JSONObject) o)
                : new Fastjson(new JSONObject());
    }

    @Override
    public <R> R getPath(String path, boolean... isThrow) {
        if(CommonUtils.isBlank(path)) return null;

        if(path.startsWith("/"))
            path = path.substring(1, path.length());

        if(path.endsWith("/"))
            path = path.substring(0, path.length()-1);

        String[] split = path.split("/", -1);
        if(!CommonUtils.isNotEmpty(split))
            throw new UtilsException("错误的Json路径" +
                    ", 例如：/store/book/title", UtilsType.JSON);

        String jsonPath = "$."+String.join(".", split);
        Object eval = JSONPath.eval(original(), jsonPath);
        isThrow(null == eval, isThrow);

        if(eval == null) return null;

        if(eval instanceof JSONArray)
            return (R) new FastJsonArray((JSONArray) eval);
        else if(eval instanceof JSONObject)
            return (R) new Fastjson((JSON) eval);
        else return (R) eval;
    }
}
