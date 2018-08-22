package com.ssitcloud.business.statistics.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.w3c.dom.Element;

public class MBeanUtil {


	public static <T> T element2Bean(Element element, T bean)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (bean == null) {
			return null;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String varName = field.getName();
			Object object = element.getAttribute(varName);
			if (object != null) {
				BeanUtils.setProperty(bean, varName, object);
			}
		}
		return bean;
	}
	/**
	 * 将元素属性对象转成POJO
	 * @methodName: element2Bean
	 * @param element
	 * @param clazz
	 * @return
	 * @returnType: T
	 * @author: liuBh
	 */
	public static <T> T element2Bean(Element element, Class<T> clazz)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, InstantiationException {
		if (clazz == null) {
			return null;
		}
		Field[] fields = clazz.getDeclaredFields();
		T t=org.springframework.beans.BeanUtils.instantiate(clazz);

		for (Field field : fields) {
			String varName = field.getName();
			Object object = element.getAttribute(varName);
			
			if (object != null) {
				BeanUtils.setProperty(t, varName, object);
			}
		}
		return t;
	}

}