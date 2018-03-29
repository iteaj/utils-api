package com.iteaj.util;

import com.iteaj.util.support.json.FastJsonAdapter;
import com.iteaj.util.support.json.jackson.JacksonAdapter;
import com.iteaj.util.support.json.JsonAdapter;
import com.iteaj.util.support.json.SimpleJson;

import java.text.DateFormat;
import java.util.List;
import java.util.Map;

public final class JsonUtils{

	private static JsonAdapter JSON_ADAPTER;

	static {

		instance();
	}

	private static void instance(){
		try {

			JSON_ADAPTER = new FastJsonAdapter();
			return;
		} catch (Exception e){

		}

		try {

			JSON_ADAPTER = new JacksonAdapter();
			return;
		} catch (Exception e) {

		}

		JSON_ADAPTER = new SimpleJson();

	}

	public static String toJson(Object obj) {
		return JSON_ADAPTER.toJson(obj);
	}

	public static String toJson(Object obj, DateFormat format) {
		return JSON_ADAPTER.toJson(obj, format);
	}

	public static  <T> T toBean(String json, Class<T> clazz) {
		return (T) JSON_ADAPTER.toBean(json, clazz);
	}

	public static  <T> List<T> toList(String json, Class<T> elementType) {
		return JSON_ADAPTER.toList(json, elementType);
	}

	public <T> T[] toArray(String json, Class<T> elementType) {
		return (T[]) JSON_ADAPTER.toArray(json, elementType);
	}

	public <K, V> Map<K, V> toMap(String json, Class<? extends Map<K, V>> mapType, Class<K> keyType, Class<V> valueType) {
		return JSON_ADAPTER.toMap(json, mapType, keyType, valueType);
	}
}
