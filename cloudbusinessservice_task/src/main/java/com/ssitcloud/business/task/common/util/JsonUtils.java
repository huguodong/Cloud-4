package com.ssitcloud.business.task.common.util;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;




/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
public class JsonUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();

	
	
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
		if(path==null||"".equals(path)) return null;
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
	 * java 对象转换为json 存入流中
	 * 
	 * @param obj
	 * @param out
	 */
	public static String toJson(Object obj) {
		if(obj==null)return null;
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
	 * 将jsonstring 转化成 jsonNode
	 * @methodName: readTree
	 * @param map
	 * @param valueType
	 * @return
	 * @returnType: T
	 * @author: liuBh
	 */
	public static JsonNode readTree(String content){
		content = replaceJsonStrNull(content);
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,Class<T> clazz){
		try {
			return objectMapper.convertValue(node, clazz);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
	}
	public static <T> T fromNode(JsonNode node,TypeReference<T> typeReference){
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
	 * @param json
	 * 
	 */
	public static <T> T fromJson(String json, Class<T> valueType) {
		json = replaceJsonStrNull(json);
		if(json==null||"".equals(json))return null;
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
		if(json==null||"".equals(json))return null;
		T obj = null;
		try {
			obj = objectMapper.readValue(json, valueTypeRef);
		} catch (Exception e) {
			ifNULLThenNew();
			throw new RuntimeException(e);
		}
		return obj;
	}
	public static <T> T convertMap(Object obj,TypeReference<T> t){
		if(obj==null||t==null) return null;
		try {
			return objectMapper.convertValue(obj, t);
		} catch (Exception e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
	}
	
	private static synchronized void ifNULLThenNew() {
		if (objectMapper == null) {
			objectMapper = new ObjectMapper();
		}
	}
	
	/**
	 * 检查jsonArray的格式
	 * author huanghuang
	 * 2017年3月2日 下午4:30:24
	 * @param targetStr
	 * @return
	 */
	public static boolean checkJsonArray(String targetStr) {
		if(StringUtils.isBlank(targetStr)){
			return false;
			
		}else{
			char firstChar = targetStr.substring(0, 1).toCharArray()[0];  
			char endChar = targetStr.substring(targetStr.length()-1).toCharArray()[0];
			if(('['==firstChar)&&(']'==endChar))
				return true;
			return false;
		}
			
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