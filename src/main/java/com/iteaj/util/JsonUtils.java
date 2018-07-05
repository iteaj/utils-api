package com.iteaj.util;

import com.iteaj.util.core.UtilsGlobalDefaultFactory;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.Node;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public final class JsonUtils{


	public static String toJson(Object obj) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().toJson(obj);
	}

	public static String toJson(Object obj, SimpleDateFormat format) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().toJson(obj, format);
	}

	public static  <T> T toBean(String json, Class<T> clazz) {
		return (T) UtilsGlobalDefaultFactory.getDefaultJsonAdapter().toBean(json, clazz);
	}

	public static  <T> List<T> toList(String json, Class<T> elementType) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().toList(json, elementType);
	}

	public <T> T[] toArray(String json, Class<T> elementType) {
		return (T[]) UtilsGlobalDefaultFactory.getDefaultJsonAdapter().toArray(json, elementType);
	}

	public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter()
				.toMap(json, mapType, keyType, valueType);
	}

	public static Json buildJson() {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().build();
	}

	public static Json buildJson(String json) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().build(json);
	}

	public static Node buildNode(String name, Object val) {
		return UtilsGlobalDefaultFactory.getDefaultJsonAdapter().buildNode(name, val);
	}

}
