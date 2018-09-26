package com.iteaj.util.module.json;

import com.iteaj.util.CommonUtils;
import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create time: 2018/9/19
 *
 * @author iteaj
 * @since 1.0
 */
public abstract class AbstractJson<T> implements Json<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public short getShort(String name, boolean... isThrow) {
        return this.getShort(name, (short)0, isThrow);
    }

    @Override
    public int getInt(String name, boolean... isThrow) {
        return this.getInt(name, 0, isThrow);
    }

    @Override
    public long getLong(String name, boolean... isThrow) {
        return this.getLong(name, 0l, isThrow);
    }

    @Override
    public float getFloat(String name, boolean... isThrow) {
        return this.getFloat(name, 0.0f, isThrow);
    }

    @Override
    public double getDouble(String name, boolean... isThrow) {
        return this.getDouble(name, 0.0, isThrow);
    }

    @Override
    public String getString(String name, boolean... isThrow) {
        return this.getString(name, null, isThrow);
    }

    /**
     *
     * @param condition  是否满足抛出异常的条件
     * @param isThrow   如果是true则判断{@code condition}参数 <br>
     *                  如果 condition=true则抛出异常, 否则不抛
     */
    protected void isThrow(boolean condition, boolean... isThrow) {
        boolean b = CommonUtils.isNotEmpty(isThrow) ? isThrow[0] : false;
        if(b && condition) throw new UtilsException("Json节点缺失或类型不匹配" +
                ", 如果您希望不抛异常则请指定参数isThrow=false", UtilsType.JSON);
    }
}
