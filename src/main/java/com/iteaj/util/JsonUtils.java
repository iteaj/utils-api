package com.iteaj.util;

import com.iteaj.util.core.UtilsGlobalFactory;
import com.iteaj.util.module.json.Json;

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

	public static <T> T toArray(String json, Class<T> arrayType) {
		return (T)UtilsGlobalFactory.getDefaultJsonAdapter().toArray(json, arrayType);
	}

	public static  <K, V> Map<K, V> toMap(String json, Class<? extends Map> mapType, Class<K> keyType, Class<V> valueType) {
		return UtilsGlobalFactory.getDefaultJsonAdapter()
				.toMap(json, mapType, keyType, valueType);
	}

	public static Json builder() {
		return UtilsGlobalFactory.getDefaultJsonAdapter().builder();
	}

	public static Json builder(String json) {
		return UtilsGlobalFactory.getDefaultJsonAdapter().builder(json);
	}

}
