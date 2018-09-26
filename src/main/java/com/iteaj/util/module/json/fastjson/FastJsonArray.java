package com.iteaj.util.module.json.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
public class FastJsonArray implements JsonArray {

    private JSONArray array;

    public FastJsonArray(JSONArray array) {
        this.array = array;
        AssertUtils.isTrue(array != null, "构造FastJsonArray实例" +
                "缺失JSONArray对象", UtilsType.JSON);
    }

    @Override
    public JSONArray original() {
        return this.array;
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public short getShort(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof Short), index, isThrow);
        return o != null && o instanceof Short ? (Short) o : 0;
    }

    @Override
    public int getInt(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof Integer), index, isThrow);
        return o != null && o instanceof Integer ? (Integer) o : 0;
    }

    @Override
    public float getFloat(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof Float), index, isThrow);
        return o != null && o instanceof Float ? (Float) o : 0.0f;
    }

    @Override
    public double getDouble(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof Double), index, isThrow);
        return o != null && o instanceof Double ? (Double) o : 0.0;
    }

    @Override
    public long getLong(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof Long), index, isThrow);
        return o != null && o instanceof Integer ? (Long) o : 0;
    }

    @Override
    public boolean getBoolean(int index, boolean... isThrow) {
        Object o = original().get(0);
        isThrow(o == null || !(o instanceof Boolean), index, isThrow);
        return o == null ? false : o instanceof Boolean ? (Boolean) o : false;
    }

    @Override
    public String getString(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null, index, isThrow);
        return o != null ? o.toString() : null;
    }

    @Override
    public Json getJson(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(o == null || !(o instanceof JSONObject), index, isThrow);
        return new Fastjson((JSONObject) o);
    }

    @Override
    public JsonArray getJsonArray(int index, boolean... isThrow) {
        Object o = original().get(index);
        isThrow(!(o instanceof JSONArray), index, isThrow);
        return o == null ? null : o instanceof JSONArray
                ? new FastJsonArray((JSONArray) o) : null;
    }

    @Override
    public <T> ArrayList<T> toList(Class<T> eleType) {
        return (ArrayList<T>) original().toJavaList(eleType);
    }

    @Override
    public <T> T toBean(Class<T> arrayType) {
        if(!arrayType.isArray())
            throw new UtilsException("反序列化JsonArray, 参数类型必须是数组类型eg：Integer[].class", UtilsType.JSON);

        return JSONObject.toJavaObject(original(), arrayType);
    }
}
