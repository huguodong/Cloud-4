package com.ssitcloud.common.entity;

import java.io.Serializable;

/** 
 * 接口返回的result类
 * 
 * 同ResultEntity.java，转成泛型
 * @param <T>
 *
 */
public class ResultEntityF<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private boolean state = false;
	private String message = "";
	private String retMessage = "";
	private T result ;
	
	public ResultEntityF() {
	}

	public ResultEntityF(String retMessage, T result) {
		this.retMessage = retMessage;
		this.result = result;
	}

	public ResultEntityF(boolean state, String message, T result) {
		this.state = state;
		this.message = message;
		this.result = result;
	}

	public ResultEntityF(boolean state, String message, String retMessage,
			T result) {
		this.state = state;
		this.message = message;
		this.retMessage = retMessage;
		this.result = result;
	}

	public boolean isState() {
		return state;
	}
	
	public boolean getState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRetMessage() {
		return retMessage;
	}
	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
}
