package com.ssitcloud.app_reader.common.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Created by LXP on 2017/3/6.
 * *
 */

public class JsonUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    /**
     * java 对象转换为json 存入流中
     *
     * @param obj
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
     * @param content json字符串
     * @return jsonNode对象
     */
    public static JsonNode readTree(String content){
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
     * @param valueTypeRef
     */
    public static <T> T fromJson(String json, TypeReference<T> valueTypeRef) {
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
}
