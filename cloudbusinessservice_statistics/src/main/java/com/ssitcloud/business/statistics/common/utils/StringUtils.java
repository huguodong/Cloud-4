package com.ssitcloud.business.statistics.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串验证工具类
 * @author LXP
 * @version 创建时间：2017年2月24日 下午5:30:22
 */
public class StringUtils {
	
	/**
	 * 字符串是否为空
	 * @param str
	 * @return null或者length==0返回true，其他返回false
	 */
	public static boolean isEmpty(String str){
		if(str == null){
			return true;
		}
		return str.isEmpty();
	}
	
	/**
	 * 是否为15或者18位身份证号
	 * @param str
	 * @return 不为null且符合身份证格式返回true，否则返回false
	 */
	public static boolean isIdNumber(String str){
		if(str == null){
			return false;
		}
		if(str.length() == 18){
			return str.matches("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)");
		}
		if(str.length() == 15){
			return str.matches("[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}");
		}
		return false;
	}
	
	/**
	 * 是否为手机号
	 * @param str 不为null且符合手机号格式返回true，否则返回false
	 * @return
	 */
	public static boolean isMobile(String str){
		if(str == null){
			return false;
		}
		return str.matches("(13\\d|14[57]|15[^4,\\D]|17[678]|18\\d)\\d{8}|170[059]\\d{7}");
	}
	
	/**
	 * 是否为邮箱
	 * @param str 不为null且符合邮箱格式返回true，否则返回false
	 * @return
	 */
	public static boolean isEmail(String str){
		if(str == null){
			return false;
		}
		return str.matches("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}");
	}
	
	public static String getIpAddr(HttpServletRequest request) {

		String ip = request.getHeader("x-forwarded-for");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}
}
