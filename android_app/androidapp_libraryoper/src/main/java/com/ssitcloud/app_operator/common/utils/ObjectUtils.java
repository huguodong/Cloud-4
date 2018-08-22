package com.ssitcloud.app_operator.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LXP on 2017/3/20.
 * 对象工具箱
 */

public class ObjectUtils {

    /**
     * 将map转换为实体
     * @param map map对象
     * @param type 实体class
     * @return if map == null return null; other return obj
     * @throws CanNotInstanceClass 找不到实体class
     * @throws NoSuchMethodException 实体没有对应map中的key时抛出
     */
    public static <T> T convertMap(Map<String, Object> map,Class<T> type) throws CanNotInstanceClass,NoSuchMethodException {
        if(map == null){
            return null;
        }

//        T obj;
//        try {
//            obj = type.newInstance();
//        } catch (InstantiationException e) {
//            throw new CanNotInstanceClass(e.getMessage());
//        } catch (IllegalAccessException e) {
//            throw new CanNotInstanceClass(e.getMessage());
//        }
//
//        Set<String> keySet = map.keySet();
//        try {
//            for (String s : keySet) {
//                Object args = map.get(s);
//                if(args != null) {
//                    String methodName = getSetMethod(s);
//                    Method method = null;
//                    boolean isDate = false;
//                    //尝试解析日期
//                    if(args.getClass() == Long.class){
//                        try{
//                            method = getSetMethod(type,methodName, Date.class);
//                            isDate = true;
//                        }catch(Exception e){
//                            method = getSetMethod(type,methodName, Timestamp.class);
//                            isDate = true;
//                        }
//                    }
//                    if(method == null){
//                        method = getSetMethod(type,methodName, args.getClass());
//                    }
//                    try {
//                        if(isDate){//是日期类
//                            method.invoke(obj,new Timestamp((Long)args));
//                        }else{
//                            method.invoke(obj,args);
//                        }
//                    } catch (IllegalAccessException e) {
//                    } catch (InvocationTargetException e) {
//                    }
//                }
//            }
//        } catch (NoSuchMethodException e) {
//            throw new NoSuchMethodException("查询不到方法 "+e.getMessage());
//        }
        String json = JsonUtils.toJson(map);
        return JsonUtils.fromJson(json,type);
    }

    public static Map<String,Object> objectToMap(Object obj){
        Class objClass = obj.getClass();
        Method[] methods = objClass.getMethods();
        Map<String,Object> data = new HashMap<>();
        for (Method method : methods) {
            String methodName = method.getName();
            if(method.getParameterTypes().length == 0
                    && methodName.length() > 3
                    && methodName.startsWith("get")
                    && !methodName.equals("getClass")){
                try {
                    Object o = method.invoke(obj);
                    data.put(methodName.toLowerCase().substring(3),o);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                }
            }
        }
        return data;
    }

    private static String getSetMethod(String s) {
        if (s.length() > 1) {
            return "set" + Character.toUpperCase(s.charAt(0)) + s.substring(1);
        } else {
            return "set" + s.toUpperCase();
        }
    }

    private static Method getSetMethod(Class objClass,String methodName,Class... argsClass) throws NoSuchMethodException{
        if(objClass == null){
            throw new NoSuchMethodException("查询不到方法 "+methodName);
        }
        try {
            return objClass.getDeclaredMethod(methodName, argsClass);
        }catch (NoSuchMethodException e){
            return getSetMethod(objClass.getSuperclass(),methodName,argsClass);
        }
    }
    /**
     * 初始化类失败
     */
    public static class CanNotInstanceClass extends Exception{
        public CanNotInstanceClass(){
            super("can Not Class");
        }
        public CanNotInstanceClass(String message){
            super(message);
        }
    }

}
