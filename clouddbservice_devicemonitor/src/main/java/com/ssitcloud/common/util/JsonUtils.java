package com.ssitcloud.common.util;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.ibatis.io.Resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
public class JsonUtils {
	public static void main(String[] args) {
		String s="{\"libid\":\"1\",\"deviceid\":\"001\",\"bin_state\":{\"cardbin\":{\"amount\":\"99\",\"state\":\"0\"},\"cashbin\":[{\"subtype\":\"50\",\"amount\":\"2\",\"state\":\"0\"},{\"subtype\":\"100\",\"amount\":\"5\",\"state\":\"0\"}],\"bookbin\":[{\"binno\":\"1\",\"subtype\":\"1\",\"desc\":\"海洋类\",\"amount\":\"1\",\"state\":\"0\"},{\"binno\":\"2\",\"subtype\":\"1\",\"desc\":\"海洋类\",\"amount\":\"1\",\"state\":\"0\"},{\"binno\":\"3\",\"subtype\":\"1\",\"desc\":\"海洋类\",\"amount\":\"1\",\"state\":\"0\"},{\"binno\":\"4\",\"subtype\":\"2\",\"desc\":\"工具类\",\"amount\":\"1\",\"state\":\"0\"},{\"binno\":\"5\",\"subtype\":\"10\",\"desc\":\"未分类\",\"amount\":\"1\",\"state\":\"0\"}]},\"ext_state\":{\"IdentityReader\":\"0\",\"AuthorityReader\":\"0\",\"ItemRfidReader\":\"0\",\"ItemLoadReader\":\"0\",\"RegisterReader\":\"0\",\"CashAcceptor\":\"0\",\"CardDispenser\":\"0\",\"Printer\":\"0\",\"PlcSSL\":{\"Shelf1\":\"0\",\"Shelf2\":\"0\",\"Shelf3\":\"0\",\"Clutch1\":\"0\",\"Clutch2\":\"0\",\"Clutch3\":\"0\",\"Pushhandle1\":\"0\",\"Pushhandle2\":\"0\",\"Pushhandle3\":\"0\",\"Shelf\":\"0\",\"Bin\":\"0\",\"Belt\":\"0\"}}}";
		Map<String, Object> m=JsonUtils.fromJson(s, Map.class);
		
	}
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
			return objectMapper.readValue(Resources.getResourceAsStream(path),
					typeReference);
		} catch (IOException e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
	}
	
	public static <T> T convertValue(Object fromValue, Class<T> toValueType){
		if(fromValue==null||toValueType==null) return null;
		return objectMapper.convertValue(fromValue, toValueType);
	}
	/**
	 * 写到文件中
	 * @author: liubh
	 * @param resultFile
	 * @param obj
	 */
	public  static void writeToFile(File resultFile,Object obj){
		try {
			 objectMapper.writeValue(resultFile,obj);
		} catch (IOException e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
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
		return objectMapper.writeValueAsString(obj);
	}
}