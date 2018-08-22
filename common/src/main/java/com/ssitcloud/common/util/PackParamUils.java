package com.ssitcloud.common.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * 封装request参数
 * 
 * @author zengtp 2014-5-30
 */
public class PackParamUils {

	public static Map<String, String> packParam(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String[]> param = request.getParameterMap();
		if (param != null && !param.isEmpty()){
			// 遍历
			for (Iterator<Entry<String, String[]>> iter = param.entrySet().iterator(); iter.hasNext();) {
				Entry<String, String[]> element = iter.next();
				String strKey = element.getKey();
				String[] value = (String[])element.getValue();
				map.put(strKey, value[0]);
			}
		}
		return map;
	}

	/**
	 * 封装request参数
	 * 
	 * @param request
	 * @param clazz
	 * @return
	 */
	public static <T> T packParam(HttpServletRequest request, Class<T> clazz) {
		Map<String, String[]> map = request.getParameterMap();
		if (map == null || map.isEmpty()) return null;
		// 生成对象，默认有无参构造方法
		T t;
		try {
			t = clazz.newInstance();
			Method[] methods = clazz.getMethods();
			for (Entry<String, String[]> entry : map.entrySet()) {
				for (Method method : methods) {
					if (("set" + entry.getKey()).equals(method.getName()) || ("set" + toUpperCaseFirstOne(entry.getKey())).equals(method.getName())) {
						if (method.getParameterTypes()[0] == Integer.class || int.class == method.getParameterTypes()[0]) {
							String string = entry.getValue()[0];
							if (string != null && !"".equals(string)) {
								method.invoke(t, Integer.valueOf(string));
							}
						} else if (method.getParameterTypes()[0] == List.class) {
							if ("setRows".equals(method.getName())) {
								List<Object> arrayList = new ArrayList<Object>();
								arrayList.add(entry.getValue()[0]);
								method.invoke(t, arrayList);
							}
						} else if (method.getParameterTypes()[0] == double.class || Double.class == method.getParameterTypes()[0]) {
							String string = entry.getValue()[0];
							if (string != null && !"".equals(string)) {
								method.invoke(t, Double.valueOf(string));
							}
						} else {
							method.invoke(t, entry.getValue()[0]);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return t;
	}

	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) return s;
		else return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

}
