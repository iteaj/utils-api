package com.iteaj.util;

import com.iteaj.util.json.JsonAdapter;
import com.iteaj.util.json.JsonWrapper;
import com.iteaj.util.json.NodeWrapper;
import com.iteaj.util.json.fastjson.FastJsonAdapter;
import com.iteaj.util.json.jackson.JacksonAdapter;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

public final class JsonUtils{

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

	public static String toJson(Object obj) {
		return CURRENT_ADAPTER.toJson(obj);
	}

	public static String toJson(Object obj, DateFormat format) {
		return CURRENT_ADAPTER.toJson(obj, format);
	}

	public static  <T> T toBean(String json, Class<T> clazz) {
		return (T) CURRENT_ADAPTER.toBean(json, clazz);
	}

	public static  <T> List<T> toList(String json, Class<T> elementType) {
		return CURRENT_ADAPTER.toList(json, elementType);
	}

	public <T> T[] toArray(String json, Class<T> elementType) {
		return (T[]) CURRENT_ADAPTER.toArray(json, elementType);
	}

	public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
		return CURRENT_ADAPTER.toMap(json, mapType, keyType, valueType);
	}

	public static JsonWrapper buildJson() {
		return CURRENT_ADAPTER.build();
	}

	public static NodeWrapper buildNode(String name, Object val) {
		return CURRENT_ADAPTER.build(name, val);
	}

	public static JsonAdapter adapter() {
		if(null == CURRENT_ADAPTER)
			throw new UtilsException("Json-请导入至少一种Json库(Gson, Jackson, FastJson)", UtilsType.JSON);

		return CURRENT_ADAPTER;
	}
}
