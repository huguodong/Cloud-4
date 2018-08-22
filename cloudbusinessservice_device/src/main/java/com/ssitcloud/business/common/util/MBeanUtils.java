package com.ssitcloud.business.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

public class MBeanUtils {

	
	/**
	 * 转换List<bean> 的格式为 ReturnResultEntity 对象的格式
	 * @param beans
	 * @param tableName
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws IntrospectionException
	 */
	public static <T> ReturnResultEntity makeReturnResultEntityFromList(List<T> beans,String tableName) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, IntrospectionException{
		ReturnResultEntity ret=new ReturnResultEntity();
		List< Map<String,Object>> records=new ArrayList<>();
		if(beans==null||beans.size()==0){return null;}
		T t=beans.get(0);
		 Map<String,String> fieldMap=fields2Map(t);
		for(T bean:beans){
			records.add(bean2Map(bean));
		}
		ret.setFields(fieldMap);
		ret.setRecords(records);
		ret.setTable(tableName);
		return ret;
	}
	
	public static <T> Map<String,String> fields2Map(T bean){
		if (bean == null) {
			return null;
		}
		Map<String,String> fieldMap=new HashMap<>();
		// 获取对象对应类中的所有属性域
		Field[] fields = bean.getClass().getDeclaredFields();
		int i=1;
		for (Field field : fields) {
			if(ReflectionUtils.isPublicStaticFinal(field)){
				continue;
			}
			String fieldId=String.valueOf(i);
			fieldMap.put(field.getName(),fieldId);
			i++;
		}
		return fieldMap;
	}
	
	/**
	 * 把实体bean对象转换成Map
	 * 
	 * @param bean
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 */
	public static <T> Map<String,Object> bean2Map(T bean)
			throws IllegalArgumentException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		if (bean == null) {
			return null;
		}
		Map<String,Object> record=new HashMap<>();
		// 获取对象对应类中的所有属性域
		Field[] fields = bean.getClass().getDeclaredFields();
		
		Map<String,String> fieldMap=new HashMap<>();
		int i=1;
		for (Field field : fields) {
			if(ReflectionUtils.isPublicStaticFinal(field)){//PublicStaticFinal pass
				continue;
			}
			// 获取属性名
			String varName = field.getName();
		
			PropertyDescriptor pd = new PropertyDescriptor(varName, bean.getClass());
			// Method wM = pd.getWriteMethod();//获得写方法
		 	Method rm= pd.getReadMethod();//获得读方法
		 	
		 	Object param=rm.invoke(bean);
		 	
			String fieldId=String.valueOf(i);
			//加入fieldMap
			
			if(param==null){//如果为空则替换成"null"
				param="null";
			}
			//默认是按顺序的
			fieldMap.put(fieldId, varName);
			i++;
			if (param instanceof Integer) {// 判断变量的类型
				int value = ((Integer) param).intValue();
				record.put(fieldId, value);
			} else if (param instanceof String) {
				String value = (String) param;
				record.put(fieldId, value);
			} else if (param instanceof Double) {
				double value = ((Double) param).doubleValue();
				record.put(fieldId, value);
			} else if (param instanceof Float) {
				float value = ((Float) param).floatValue();
				record.put(fieldId, value);
			} else if (param instanceof Long) {
				long value = ((Long) param).longValue();
				record.put(fieldId, value);
			} else if (param instanceof Boolean) {
				boolean value = ((Boolean) param).booleanValue();
				record.put(fieldId, value);
			} else if (param instanceof Date) {
				Date value = (Date) param;
				record.put(fieldId, value);
			}
		}
		return record;
	}

	/**
	 * 把DBObject转换成bean对象
	 * 
	 * @param dbObject
	 * @param bean
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	/*public static <T> T dbObject2Bean(DBObject dbObject, T bean)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		if (bean == null) {
			return null;
		}
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields) {
			String varName = field.getName();
			Object object = dbObject.get(varName);
			if (object != null) {
				BeanUtils.setProperty(bean, varName, object);
			}
		}
		return bean;
	}*/

}