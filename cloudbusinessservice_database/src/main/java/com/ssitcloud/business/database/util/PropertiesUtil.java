package com.ssitcloud.business.database.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.ssitcloud.business.common.util.CommentedProperties;
import com.ssitcloud.business.common.util.LogUtils;

import net.sf.json.JSONObject;

public class PropertiesUtil {
	static String fileName = "static-config.properties";
	static CommentedProperties prop = null;

	public static void main(String[] args) {
		String val = getConfigPropValueAsText("developer_model");
		System.out.println(val);
		String filePath = getPath();
		String path = filePath.substring(0, filePath.lastIndexOf("/"));
		System.out.println(path);
		modify("developer_model", "false");
	}
	
	public static String getPath(){
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			return url.getFile();
		} catch (Exception e) {
			LogUtils.error(fileName + "查找失败.....", e);
		}
		return null;
	}

	public static String getValue(String key, String fileName) {
		InputStream input = null;
		CommentedProperties prop = new CommentedProperties();
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			prop.load(input);
		} catch (IOException e) {
			LogUtils.error("没有找到" + fileName, e);
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty(key);
	}

	/**
	 * 获取config.properties的值
	 * 
	 * @param key
	 */
	public static String getConfigPropValueAsText(String key) {
		InputStream input = null;
		CommentedProperties prop = new CommentedProperties();
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			prop.load(input);
		} catch (IOException e) {
			LogUtils.error("没有找到" + fileName, e);
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop.getProperty(key);
	}

	public static CommentedProperties loadFile() {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			prop = new CommentedProperties();
			prop.load(input, "UTF-8");
			return prop;
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
		return null;
	}

	public static String getValue(String key) {
		if (null == prop)
			loadFile();
		return prop.getProperty(key);
	}

	/**
	 * 修改配置文件
	 * 
	 * @param key
	 * @param val
	 * @return
	 */
	public static boolean modify(String key, String val) {
		FileOutputStream fos = null;
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			File file = new File(url.getFile());
			if (!file.exists())
				throw new FileNotFoundException();
			if (prop == null || prop.isEmpty())
				loadFile();
			//if (prop.getProperty(key) != null) {
				prop.setProperty(key, val);
				fos = new FileOutputStream(file);
				prop.store(fos);
				return true;
			//}
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
		}
		return false;
	}

	/**
	 * 修改配置文件
	 * 
	 * @param map
	 * @return
	 */
	public static boolean modifyByMap(LinkedHashMap<String, String> map) {
		FileOutputStream fos = null;
		CommentedProperties p = null;
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			File file = new File(url.getFile());
			if (!file.exists())
				throw new FileNotFoundException();
			if (prop == null || prop.isEmpty())
				loadFile();
			String key = null;
			p = prop.clone();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				key = entry.getKey();
				//if (p.getProperty(key) != null) {
					prop.setProperty(key, entry.getValue());
				//}
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
