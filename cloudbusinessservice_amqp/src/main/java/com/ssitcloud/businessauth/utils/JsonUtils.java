package com.ssitcloud.businessauth.utils;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


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
			return objectMapper.readValue(new ClassPathResource(path).getFile(),
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
		if(json==null&&"".equals(json))return null;
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
	/**
	 * 将对象转化为json格式的字符串
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static String object2String(Object obj) throws Exception{
		String json = objectMapper.writeValueAsString(obj);
		json = json.replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "").replaceAll(" ", "");
		return json;
	}
}