package com.ssitcloud.dbauth.service;

import com.ssitcloud.dbauth.entity.RSAEntiy;

/**
 * <p>2016年4月13日 下午7:36:17
 * @author hjc
 */
public interface RSAService {

	/**
	 * 取表中第一条数据
	 *
	 * <p>2016年4月13日 下午6:11:40
	 * <p>create by hjc
	 * @return
	 */
	public abstract RSAEntiy selRsaEntityTop();
	
	//start author by LXP,add two method
	/**
	 * 加密字符串
	 * @param eString
	 * @throws RuntimeException 加密失败时抛出
	 * @return
	 */
	String encryptionString(String eString);
	
	/**
	 * 解密字符串
	 * @param dString
	 * @throws RuntimeException 解密失败时抛出
	 * @return
	 */
	String decryptString(String dString);
	//end author by LXP,add two method
}
