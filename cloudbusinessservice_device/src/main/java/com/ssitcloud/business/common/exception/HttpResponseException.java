package com.ssitcloud.business.common.exception;

/**
 * 远端服务器响应异常,当远端服务器未能正常响应请求时应当抛出此异常
 * @author LXP
 * @version 创建时间：2017年2月23日 上午9:32:37
 */
public class HttpResponseException extends RuntimeException {
	private int responceState;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4564778078319484475L;

	public HttpResponseException() {
	}

	/**
	 * 
	 * @param message 错误信息
	 */
	public HttpResponseException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message 错误信息
	 * @param tb 异常
	 */
	public HttpResponseException(String message,Throwable tb) {
		super(message, tb);
	}

	/**
	 * 获取http响应码
	 * @return
	 */
	public int getResponceState() {
		return responceState;
	}

	/**
	 * 设置http响应码
	 * @param responceState
	 */
	public void setResponceState(int responceState) {
		this.responceState = responceState;
	}
}
