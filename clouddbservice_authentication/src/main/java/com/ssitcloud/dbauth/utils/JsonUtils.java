package com.ssitcloud.dbauth.utils;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
/**
 * JSON转换工具
 * 
 * @author lbh
 * 
 *         2016年3月23日
 */
public class JsonUtils {
	public static void main(String[] args) {
		//UserEntity u=fromJson("{\"operator_id\":\"dsdsd\",\"operator_pwd\":\"dsds\",\"rememberme\":false}", UserEntity.class);
		//String ss="{\"operator_id\":\"67\",\"operator_name\":\"67\",\"lib_id\":\"1\",\"lib_name\":\"1\",\"sox_tpl_id\":\"2\",\"operator_pwd\":\"OiGS54ajWKiiy1cF2jX86w+yfCTJQb2WhY/rb2vL8JTstE5aaGwkBLgijOGCHhAvr/6MtWVf0xgV\r\nCsLRCny+cWMVtlYQ697bhxJ51biClHJDl7gMwvkncxdf3MwLduUpBvE1Boy17glk3EWRL3EetDcl\r\nSYHZeg9S+j6SBD777Bg=\",\"operator_type\":\"1\",\"isActive\":\"1\",\"isLock\":\"1\",\"lock_time\":\"1\",\"isLogged\":\"0\",\"last_login_ip\":\"127.0.0.1\",\"last_login_time\":\"2016-04-05 16:26:08.0\",\"last_lock_time\":\"2016-04-11 16:46:17.0\",\"last_chgpwd_time\":\"2016-04-07 12:12:13.0\",\"login_fail_times\":\"2\",\"service_expire_date\":\"2016-04-30 10:21:32.0\",\"service_start_date\":\"2016-04-16 10:25:52.0\",\"faild_times\":null,\"vaild_time\":\"8:00-18:00\"}";
		//String s="{\"operator_id\":\"dsdsd\",\"operator_pwd\":\"dsds\",\"rememberme\":false}";
		//ALLOW_UNQUOTED_CONTROL_CHARS(true);
		//Operator u= fromNode(readTree(ss), Operator.class);
		//Operator o=objectMapper.convertValue(JsonUtils.fromJson(ss, Map.class), Operator.class);
		//JsonUtils.readTree("");
	}
	private static ObjectMapper objectMapper;
	static{
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);	
	}
	/**
	 * 允许特殊控制符
	 * @methodName: ALLOW_UNQUOTED_CONTROL_CHARS
	 * @param ALLOW_UNQUOTED_CONTROL_CHARS
	 * @returnType: void
	 * @author: liuBh
	 */
	public static void ALLOW_UNQUOTED_CONTROL_CHARS(boolean ALLOW_UNQUOTED_CONTROL_CHARS){
		if(objectMapper!=null){
			objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, ALLOW_UNQUOTED_CONTROL_CHARS);
		}
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
	public static <T> T convertMapToObject(Map<String,Object> map,Class<T> clazz){
		if(map==null||clazz==null) return null;
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
			ifNULLThenNew();
			new RuntimeException(e);
		}
		return null;
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