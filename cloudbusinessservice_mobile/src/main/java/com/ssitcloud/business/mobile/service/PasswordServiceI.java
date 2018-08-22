package com.ssitcloud.business.mobile.service;

/**
 * 加解密服务
 * @author LXP
 * @version 创建时间：2017年2月25日 下午2:51:17
 */
public interface PasswordServiceI {
	/**
	 * 加密服务
	 * @param str 请保证str != null
	 * @return 返回null说明加密失败
	 */
	String encryption(String str);
	
	/**
	 * 解密服务
	 * @param str 请保证str != null
	 * @return 返回null说明加密失败
	 */
	String decrypt(String str);
}
