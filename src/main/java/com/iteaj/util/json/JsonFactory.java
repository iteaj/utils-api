package com.iteaj.util.json;

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

    private static JsonAdapter JACKSON_ADAPTER;
    private static JsonAdapter FAST_JSON_ADAPTER;
    private static ClassNotFoundException EXCEPTION = new ClassNotFoundException();

    static {
        try {

            FAST_JSON_ADAPTER = new FastJsonAdapter();
        } catch (Throwable e){

        }

        try {

            JACKSON_ADAPTER = new JacksonAdapter();
        } catch (Throwable e) {

        }
    }

    public static JsonWrapper create() {
       return jsonAdapter().build();
    }

    public static JsonWrapper create(String key, Object val) {
        return jsonAdapter().build(key, val);
    }

    public static JsonAdapter jsonAdapter() {
        if(null != JACKSON_ADAPTER)
            return JACKSON_ADAPTER;

        if(null != FAST_JSON_ADAPTER)
            return FAST_JSON_ADAPTER;

        throw new IllegalStateException("请导入至少一种Json库(Gson, Jackson, FastJson)", EXCEPTION);
    }
}
