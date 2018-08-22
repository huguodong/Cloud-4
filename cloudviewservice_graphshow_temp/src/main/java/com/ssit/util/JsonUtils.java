package com.ssit.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * JSON转换工具
 * 
 */
public class JsonUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();
	private static XmlMapper xmlMapper = new XmlMapper();

	/**
	 * 允许特殊控制符
	 * 
	 * @methodName: ALLOW_UNQUOTED_CONTROL_CHARS
	 * @param ALLOW_UNQUOTED_CONTROL_CHARS
	 * @returnType: void
	 * @author: liuBh
	 */
	public static void ALLOW_UNQUOTED_CONTROL_CHARS(boolean ALLOW_UNQUOTED_CONTROL_CHARS) {
		if (objectMapper != null) {
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
		if (path == null || "".equals(path))
			return null;
		try {
			return objectMapper.readValue(ResourcesUtil.getInputStream(path), typeReference);
		} catch (IOException e) {
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
	public static <T> T convertMapToObject(Map<String, Object> map, Class<T> clazz) {
		if (map == null || clazz == null)
			return null;
		try {
			return objectMapper.convertValue(map, clazz);
		} catch (Exception e) {
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
	 * 
	 * @methodName: readTree
	 * @param map
	 * @param valueType
	 * @return
	 * @returnType: T
	 * @author: liuBh
	 */
	public static JsonNode readTree(String content) {
		try {
			return objectMapper.readTree(content);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromNode(JsonNode node, Class<T> clazz) {
		try {
			return objectMapper.convertValue(node, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromNode(JsonNode node, TypeReference<T> typeReference) {
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
	 * @param obj
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

	/**
	 * json转xml
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	public static String json2xml(String jsonStr) throws Exception {
		JsonNode root = objectMapper.readTree(jsonStr);
		return xmlMapper.writeValueAsString(root);
	}

	/**
	 * xml转json
	 * 
	 * @param xml
	 * @return
	 */
	public static String xml2json(String xml) {
		StringWriter w = new StringWriter();
		JsonParser jp = null;
		JsonGenerator jg = null;
		try {
			jp = xmlMapper.getFactory().createParser(xml);
			jg = objectMapper.getFactory().createGenerator(w);
			while (jp.nextToken() != null) {
				jg.copyCurrentEvent(jp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(jg);
			close(jp);
		}

		return w.toString();
	}

	private static void close(Closeable obj) {
		if (obj != null) {
			try {
				obj.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}