package com.ssit.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.Consts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {
	private static String fileName = "config.properties";
	public static Properties config;

	static {
		try {
			config = PropertiesLoaderUtils.loadProperties(new EncodedResource(new ClassPathResource(fileName), Consts.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加载properties文件，并获取值
	 * 
	 * @param key
	 * @param fileName
	 */
	public static String getValue(String key, String fileName) {
		CommentedProperties prop = loadFile(fileName);
		return prop.getProperty(key);
	}

	/**
	 * 获取config.properties的值
	 * 
	 * @param key
	 */
	public static String getValue(String key) {
		CommentedProperties prop = loadFile(fileName);
		return prop.getProperty(key);
	}

	/**
	 * 获取config.properties的值
	 * 
	 * @param prop
	 * @param key
	 */
	public static String getValue(CommentedProperties prop, String key) {
		if (prop != null)
			return prop.getProperty(key);
		return null;
	}

	/**
	 * 加载properties文件
	 * 
	 */
	public static CommentedProperties loadFile(String fileName) {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			CommentedProperties prop = new CommentedProperties();
			prop.load(input, "UTF-8");
			return prop;
		} catch (IOException e) {
			System.err.println(fileName + "加载失败.....");
			return null;
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
	 * 获取properties的值
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getPropertiesValue(String key, String defaultValue) {
		return config.getProperty(key, defaultValue);
	}

	/**
	 * 获取properties的值
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertiesValue(String key) {
		return config.getProperty(key);
	}

	/**
	 * 获取properties的值
	 * 
	 * @param key
	 * @return
	 */
	public static boolean getBooleanValue(String key) {
		String text = config.getProperty(key);
		return Boolean.parseBoolean(text);
	}
}
