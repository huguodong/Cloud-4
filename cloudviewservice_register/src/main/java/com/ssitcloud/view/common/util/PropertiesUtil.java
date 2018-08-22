package com.ssitcloud.view.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import net.sf.json.JSONObject;

public class PropertiesUtil {
	static String fileName = "config.properties";
	static CommentedProperties prop = null;

	public static void main(String[] args) {
		String val=getConfigPropValueAsText("developer_model");
		System.out.println(val);
	}
	
	public static String getValue(String key,String fileName){
		InputStream input =Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		Properties prop=new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			LogUtils.error("没有找到"+fileName, e);
			return null;
		}
		return (String) prop.get(key);
	}
	/**
	 * 获取config.properties的值
	 * @param key
	 */
	public  static String getConfigPropValueAsText(String key){
		InputStream input =Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
		Properties prop=new Properties();
		try {
			prop.load(input);
		} catch (IOException e) {
			LogUtils.error("没有找到config.properties", e);
			return null;
		}
		return (String) prop.get(key);
	}
	
	/**
	 * 加载properties文件
	 */
	public static void loadFile() {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			prop = new CommentedProperties();
			prop.load(input);
		} catch (IOException e) {
			LogUtils.error(fileName + "加载失败.....", e);
			prop = null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 从properties里面获取值
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		if (null == prop)
			loadFile();
		return prop.getProperty(key);
	}
	
	/**
	 * 修改配置文件
	 * 
	 * @param json
	 * @return
	 */
	public static boolean modifyByJSON(JSONObject json) {
		FileOutputStream fos = null;
		CommentedProperties p = null;
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			File file = new File(url.getFile());
			if (!file.exists())
				throw new FileNotFoundException();
			if (prop == null || prop.isEmpty())
				loadFile();
			Iterator<?> iterator = json.keys();
			String key = null;
			p = prop.clone();
			while (iterator.hasNext()) {
				key = String.valueOf(iterator.next());
				if (p.getProperty(key) != null) {
					p.setProperty(key, json.optString(key));
				}
			}
			if (!p.equals(prop)) {
				prop = p;
				fos = new FileOutputStream(file);
				prop.store(fos);
				return true;
			}
		} catch (Exception e) {
			LogUtils.error(fileName + "修改失败.....", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (p != null) {
				p.clear();
				p = null;
			}
		}
		return false;
	}
}
