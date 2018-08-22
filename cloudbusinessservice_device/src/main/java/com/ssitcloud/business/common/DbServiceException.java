package com.ssitcloud.business.common;

/**
 * 远程服务器异常，当通过http请求远程服务器发生错误，使用此异常
 * @author LXP
 *
 */
public class DbServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dbServiceName;//远程服务器地址
	
	public DbServiceException() {
		super();
	}
	/**
	 * @param message 异常信息
	 * @param dbServiceName 远程服务器地址
	 */
	public DbServiceException(String message,String dbServiceName) {
		super(message);
		this.dbServiceName = dbServiceName;
	}
	@Override
	public String toString() {
		return super.toString()+"\n远程服务器地址==》"+dbServiceName;
	}
}
