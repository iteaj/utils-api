package com.iteaj.util.module.json;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;

import java.util.ArrayList;

/**
 * create time: 2018/9/20
 *
 * @author iteaj
 * @since 1.0
 */
public interface JsonArray {

    <T> T original();

    int size();

    short getShort(int index, boolean... isThrow);

    int getInt(int index, boolean... isThrow);

    float getFloat(int index, boolean... isThrow);

    double getDouble(int index, boolean... isThrow);

    long getLong(int index, boolean... isThrow);

    boolean getBoolean(int index, boolean... isThrow);

    String getString(int index, boolean... isThrow);

    Json getJson(int index, boolean... isThrow);

    JsonArray getJsonArray(int index, boolean... isThrow);

    <T> ArrayList<T> toList(Class<T> eleType);

    /**
     * @param arrayType eg: Integer[].class
     * @param <T> eg: Integer[]
     * @return eg: [2,3,4]
     */
    <T> T toBean(Class<T> arrayType);

    default void isThrow(boolean condition, int index, boolean... isThrow) throws UtilsException {
        if(index < 0 || index >= size()) throw new
                UtilsException("索引Index超出范围", UtilsType.JSON);

        boolean isThrow1 = CommonUtils.isNotEmpty(isThrow)
                ? isThrow[0] : false;

        if(condition && isThrow1)
            throw new UtilsException("Json节点缺失或类型不匹配" +
                    ", 如果您希望不抛异常则请指定参数isThrow=false", UtilsType.JSON);
    }
}
