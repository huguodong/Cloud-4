package com.ssitcloud.common;

/**
 * Http请求超时异常，当进行Http请求时发生连接超时和响应超时时应当抛出此异常
 * @author LXP
 * @version 创建时间：2017年2月23日 上午9:27:32
 */
public class HttpTimeoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390543639417146218L;

	public HttpTimeoutException() {
	}
	
	/**
	 * 
	 * @param message 错误信息
	 */
	public HttpTimeoutException(String message) {
		super(message);
	}

	
	/**
	 * 
	 * @param message 错误信息
	 * @param tb 异常
	 */
	public HttpTimeoutException(String message,Throwable tb) {
		super(message, tb);
	}
	
}
