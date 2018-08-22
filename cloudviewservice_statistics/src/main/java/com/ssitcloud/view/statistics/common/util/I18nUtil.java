package com.ssitcloud.view.statistics.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssitcloud.common.entity.SystemEntity;
import com.ssitcloud.view.statistics.common.system.BeanFactoryHelper;

public class I18nUtil {

	private static  SystemEntity system=BeanFactoryHelper.getBean("systemEntity",SystemEntity.class);
	
	/**
	 * 获取语言设置
	 */
	public static String getLanguage(){
		if(system==null){
			system=BeanFactoryHelper.getBean("systemEntity",SystemEntity.class);
		}
		return system.getSystemLanguage();
	}
	/**
	 * 从JSON字符串中获取对应的语言 
	 */
	public static String converLanguage(String textJson){
		if(textJson!=null&&!"".equals(textJson)){
			JsonNode node=JsonUtils.readTree(textJson);
			JsonNode langNode=node.get(getLanguage());
			if(langNode!=null){
				return langNode.asText();
			}
		}
		return textJson;
	}
	/**
	 * 将逗号分隔的json 语言 数据 取出 
	 * @param delimiterString
	 * @return
	 */
	public static String converDelimiterStringLanguage(String delimiterString){
		String localLang=getLanguage();
		StringBuilder sb=new StringBuilder();
		if(localLang!=null){
		        Pattern p = Pattern.compile("(\"\\w+\"):(\"[^\"]+\")");  
		        Matcher m = p.matcher(delimiterString);  
		        String[] strs = null;  
		        while (m.find()) {  
		            strs = m.group().split(":");  
		            if (strs.length == 2) {  
		                String lang=strs[0].replaceAll("\"", "").trim();
		                if(localLang.equals(lang)){//zh_CN 根据内存中的实际情况来
		                	String content=strs[1].trim().replaceAll("\"", "");
		                	sb.append(content).append(",");
		                }
		            }  
		        }
		}
		if(sb.length()>1){
			return sb.substring(0, sb.length()-1);
		}
		return delimiterString;
	}
	
}
