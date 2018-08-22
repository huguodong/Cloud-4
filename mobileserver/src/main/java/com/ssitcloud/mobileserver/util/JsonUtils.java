package com.ssitcloud.mobileserver.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Map;
/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 * 2016年3月23日
 */
public class JsonUtils {
	private static final ObjectMapper objectMapper;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);	
	}
//	/**
//	 * 允许特殊控制符
//	 * @methodName: ALLOW_UNQUOTED_CONTROL_CHARS
//	 * @param ALLOW_UNQUOTED_CONTROL_CHARS
//	 * @returnType: void
//	 * @author: liuBh
//	 */
//	public static void ALLOW_UNQUOTED_CONTROL_CHARS(boolean ALLOW_UNQUOTED_CONTROL_CHARS){
//		if(objectMapper!=null){
//			objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, ALLOW_UNQUOTED_CONTROL_CHARS);
//		}
//	}

	/**
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T convertMapToObject(Map<String,Object> map,Class<T> clazz){
		if(map==null||clazz==null) return null;
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return null;
	}
	
	public static <T> T convertMap(Object obj,TypeReference<T> t){
		if(obj==null||t==null) return null;
		try {
			return objectMapper.convertValue(obj, t);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return null;
	}
	/**
	 * java 对象转换为json 存入流中
	 * 
	 * @param obj
	 */
	public static String toJson(Object obj) {
		if (obj == null)
			return null;
		String s = null;
		try {
			s = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return s;
	}

	/**
	 * json 转为java对象
	 * 
	 * @param <T>
	 * @param json
	 * 
	 */
	public static <T> T fromJson(String json, Class<T> valueType) {
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
	/**
	 * 将jsonstring 转化成 jsonNode
	 * @methodName: readTree
	 * @return
	 * @returnType: T
	 * @author: liuBh
	 */
	public static JsonNode readTree(String content){
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,Class<T> clazz){
		try {
			return objectMapper.convertValue(node, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,TypeReference<T> typeReference){
		try {
			return objectMapper.convertValue(node, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * json 转为java对象
	 * 
	 * @param <T>
	 * 
	 * @param json
	 * @param valueTypeRef
	 */
	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return obj;
	}
}