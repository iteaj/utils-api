package com.iteaj.util;

import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.module.json.Json;
import com.iteaj.util.module.json.Node;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public final class JsonUtils{


	public static String toJson(Object obj) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().toJson(obj);
	}

	public static String toJson(Object obj, SimpleDateFormat format) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().toJson(obj, format);
	}

	public static  <T> T toBean(String json, Class<T> clazz) {
		return (T) UtilsGlobalFactory.getDefaultJsonAdapter().toBean(json, clazz);
	}

	public static  <T> List<T> toList(String json, Class<T> elementType) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().toList(json, elementType);
	}

	public <T> T[] toArray(String json, Class<T> elementType) {
		return (T[]) UtilsGlobalFactory.getDefaultJsonAdapter().toArray(json, elementType);
	}

	public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
		return UtilsGlobalFactory.getDefaultJsonAdapter()
				.toMap(json, mapType, keyType, valueType);
	}

	public static Json buildJson() {
		return UtilsGlobalFactory.getDefaultJsonAdapter().build();
	}

	public static Json buildJson(String json) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().build(json);
	}

	public static Node buildNode(String name, Object val) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().buildNode(name, val);
	}

}
