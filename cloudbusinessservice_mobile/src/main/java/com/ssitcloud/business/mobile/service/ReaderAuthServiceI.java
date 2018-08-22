package com.ssitcloud.business.mobile.service;

import javax.servlet.http.HttpServletRequest;

import com.ssitcloud.business.mobile.service.impl.ReaderAuthServiceImpl;
import com.ssitcloud.business.mobile.service.impl.ReaderAuthServiceImpl.PasswordServiceFailException;

/**
 * 读者识别服务，用于识别app是否已经登陆
 * @author LXP
 * @version 创建时间：2017年3月28日 上午8:56:26
 */
public interface ReaderAuthServiceI {
	
	/**
	 * 生成识别码
	 * @param idx 
	 * @param pwd 
	 * @return 
	 */
	String generateAuthCode(Integer idx,String pwd) throws PasswordServiceFailException;
	
	/**
	 * 识别是否有权访问
	 * @param authCode 识别码
	 * @param request 若request不为null为严格模式，会校验json参数中的reeader_idx是否一致
	 * @return
	 */
	boolean auth(String authCode,HttpServletRequest request) throws PasswordServiceFailException;

    ReaderAuthServiceImpl.AuthCode getAuthCode(String auth);
}
