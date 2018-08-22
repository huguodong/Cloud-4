package com.ssitcloud.business.mobile.service;

import com.ssitcloud.mobile.entity.EmailEntity;


/**
 * 邮件发送服务
 * @author LXP
 *
 */
public interface EmailServiceI {
	/**
	 * 发送邮件
	 * @param email
	 * @return 邮件是否发送成功
	 */
	boolean sendEnail(EmailEntity email);
	
	/**
	 * 发送注册验证码邮件
	 * @param email
	 * @return 邮件是否发送成功
	 */
	boolean sendRegisterEmail(String email,String content);
	
	/**
	 * 发送找回密码邮件
	 * @param email
	 * @return 邮件是否发送成功
	 */
	boolean sendPasswordEmail(String email,String content);
}
