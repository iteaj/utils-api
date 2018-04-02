package com.iteaj.util.json;

import com.iteaj.util.UtilsException;
import com.iteaj.util.UtilsType;
import com.iteaj.util.json.fastjson.FastJsonAdapter;
import com.iteaj.util.json.jackson.JacksonAdapter;

/**
 * create time: 2018/3/29
 *
 * @author iteaj
 * @version 1.0
 * @since 1.7
 */
public class JsonFactory {

    private static JsonAdapter CURRENT_ADAPTER;
    private static JsonAdapter JACKSON_ADAPTER;
    private static JsonAdapter FAST_JSON_ADAPTER;

    static {
        try {
            JACKSON_ADAPTER = new JacksonAdapter();
            CURRENT_ADAPTER = JACKSON_ADAPTER;
        } catch (Throwable e) {

        }
        try {
            FAST_JSON_ADAPTER = new FastJsonAdapter();
            if(null == CURRENT_ADAPTER)
                CURRENT_ADAPTER = FAST_JSON_ADAPTER;
        } catch (Throwable e){

        }

    }

    public static JsonWrapper createJson() {
       return adapter().build();
    }

    public static NodeWrapper createNode(String name, Object val) {
        return adapter().build(name, val);
    }

    public static JsonAdapter adapter() {
        if(null == CURRENT_ADAPTER)
            throw new UtilsException("Json-请导入至少一种Json库(Gson, Jackson, FastJson)", UtilsType.JSON);

        return CURRENT_ADAPTER;
    }
}
