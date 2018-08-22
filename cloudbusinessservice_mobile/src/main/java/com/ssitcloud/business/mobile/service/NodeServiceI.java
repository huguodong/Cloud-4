package com.ssitcloud.business.mobile.service;

/**
 * 
 * @author LXP
 *
 */
public interface NodeServiceI {
	/**
	 * 返回邮件节点地址
	 * @param 图书馆idx
	 * @return 邮件节点地址，若为null则获取失败
	 */
	String getEmailUrl(Integer libraryIdx);
}
