package com.ssitcloud.dblib.common.utils;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;

/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
public class JsonUtils {
	public static void main(String[] args) {
	}

	private static ObjectMapper objectMapper;
	static {
		objectMapper = new ObjectMapper();
		objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	/**
	 * 读取json文件
	 * 
	 * @methodName: fromFile
	 * @param path
	 * @param typeReference
	 * @return
	 * @returnType: T
	 * @author: liubh
	 * @createTime: 2016年4月2日
	 * @description: TODO
	 */
	public static <T> T fromFile(String path, TypeReference<T> typeReference) {
		path = replaceJsonStrNull(path);
		if (path == null || "".equals(path))
			return null;
		try {
			return objectMapper.readValue(ResourcesUtil.getInputStream(path),
					typeReference);
		} catch (IOException e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T convertMapToObject(Map<String, Object> map,
			Class<T> clazz) {
		if (map == null || clazz == null)
			return null;
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
	}

	public static <T> T convertMap(Object obj, TypeReference<T> t) {
		if (obj == null || t == null)
			return null;
		try {
			return objectMapper.convertValue(obj, t);
		} catch (Exception e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
	}

	/**
	 * java 对象转换为json 存入流中
	 * 
	 * @param obj
	 * @param out
	 */
	public static String toJson(Object obj) {
		if (obj == null)
			return null;
		String s = null;
		try {
			s = objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			ifNULLThenNew();
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
		json = replaceJsonStrNull(json);
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueType);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
		return obj;
	}

	/**
	 * 将jsonstring 转化成 jsonNode
	 * 
	 * @methodName: readTree
	 * @param map
	 * @param valueType
	 * @return
	 * @returnType: T
	 * @author: liuBh
	 */
	public static JsonNode readTree(String content) {
		content = replaceJsonStrNull(content);
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromNode(JsonNode node, Class<T> clazz) {
		try {
			return objectMapper.convertValue(node, clazz);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromNode(JsonNode node, TypeReference<T> typeReference) {
		try {
			return objectMapper.convertValue(node, typeReference);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
	}

	/**
	 * json 转为java对象
	 * 
	 * @param <T>
	 * 
	 * @param json
	 * @param obj
	 * @param valueTypeRef
	 */
	public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
		json = replaceJsonStrNull(json);
		if (json == null || "".equals(json))
			return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
		return obj;
	}

	private static synchronized void ifNULLThenNew() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
		objectMapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
	private static String replaceJsonStrNull(String json){
		if(json == null || "".equals(json)){
			return json;
		}
		json = json.replaceAll("\"[^\"]+\":null,", "");
		//json = json.replaceAll("\"[^\"]+\":null", "");
		//json = json.replaceAll(",\"[^\"]+\":null,", "");
		json = json.replaceAll(",\"[^\"]+\":null", "");
		return json;
	}
}