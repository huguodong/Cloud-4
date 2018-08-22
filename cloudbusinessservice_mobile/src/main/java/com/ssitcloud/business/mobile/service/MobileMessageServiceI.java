package com.ssitcloud.business.mobile.service;

/**
 * 手机短信服务
 * 
 * @author xunpengliu
 * @version 创建时间：2017年5月23日 上午11:07:07
 */
public interface MobileMessageServiceI {

	/**
	 * 发送注册短信服务
	 * 
	 * @return 是否发送成功
	 */
	boolean sendRegisterMessage(String mobile, String vcode);

	/**
	 * 发送找回密码邮件
	 * 
	 * @return 是否发送成功
	 */
	boolean sendPasswordMessage(String mobile, String vcode);

	/**
	 * 发送普通短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	boolean sendSMS(String mobile, String content);

	/**
	 * 以图书馆账号发送普通短信
	 * 
	 * @param mobile
	 * @param content
	 * @return
	 */
	boolean sendSMSByLibrary(String mobile, String content);
	
	/**
	 * 使用指定模板接口发短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	boolean sendSMSOnTemplate(String mobile, String content);
}
