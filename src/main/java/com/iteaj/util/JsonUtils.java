package com.iteaj.util;

import com.iteaj.util.core.UtilsException;
import com.iteaj.util.core.UtilsManagerFactory;
import com.iteaj.util.core.UtilsType;
import com.iteaj.util.module.json.JsonAdapter;
import com.iteaj.util.module.json.JsonWrapper;
import com.iteaj.util.module.json.NodeWrapper;
import com.iteaj.util.module.json.fastjson.FastJsonAdapter;
import com.iteaj.util.module.json.jackson.JacksonAdapter;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

public final class JsonUtils{


	public static String toJson(Object obj) {
		return UtilsManagerFactory.getDefaultJsonAdapter().toJson(obj);
	}

	public static String toJson(Object obj, DateFormat format) {
		return UtilsManagerFactory.getDefaultJsonAdapter().toJson(obj, format);
	}

	public static  <T> T toBean(String json, Class<T> clazz) {
		return (T) UtilsManagerFactory.getDefaultJsonAdapter().toBean(json, clazz);
	}

	public static  <T> List<T> toList(String json, Class<T> elementType) {
		return UtilsManagerFactory.getDefaultJsonAdapter().toList(json, elementType);
	}

	public <T> T[] toArray(String json, Class<T> elementType) {
		return (T[]) UtilsManagerFactory.getDefaultJsonAdapter().toArray(json, elementType);
	}

	public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
		return UtilsManagerFactory.getDefaultJsonAdapter()
				.toMap(json, mapType, keyType, valueType);
	}

	public static JsonWrapper buildJson() {
		return UtilsManagerFactory.getDefaultJsonAdapter().build();
	}

	public static JsonWrapper buildJson(String json) {
		return UtilsManagerFactory.getDefaultJsonAdapter().build(json);
	}

	public static NodeWrapper buildNode(String name, Object val) {
		return UtilsManagerFactory.getDefaultJsonAdapter().buildNode(name, val);
	}

}
