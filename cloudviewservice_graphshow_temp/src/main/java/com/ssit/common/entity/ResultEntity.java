package com.ssit.common.entity;

import java.io.Serializable;

/**
 * 接口返回的result类
 * 
 * <p>
 * 包括 state，message，retMessage，result属性。
 * <p>
 * state为接口返回的状态包含true、false;
 * <p>
 * message为接口返回的个人编的提示消息;
 * <p>
 * retMessage为组合提示消息包含函数名+系统返回的错误提醒。例："CheckFin()函数异常" + ex.getMessage();
 * <p>
 * result为实际返回的信息字符串（经过json序列化后的字符串），不同接口的序列化对象不同
 * <p>
 * 2016年3月30日 上午9:22:17
 * 
 * @author hjc
 * @param <T>
 * 
 */
public class ResultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean state = false;
	private String message = "";
	private String retMessage = "";
	private Object result;

	public ResultEntity(){}
	
	public ResultEntity(String message){this.message=message;}
	
	public ResultEntity(boolean state,String message){
		this.state=state;
		this.message=message;
	}
	
	public void setValue(boolean state,String message, String retMessage, Object result) {
		setState(state);
		setMessage(message);
		setRetMessage(retMessage);
		setResult(result);
	}
	
	public void setValue(boolean state,String message, String retMessage) {
		setState(state);
		setMessage(message);
		setRetMessage(retMessage);
	}
	/**
	 * 设置结果集的值
	 * 
	 * <p>2016年4月11日 上午11:33:55
	 * <p>create by hjc
	 * @param state 为接口返回的状态包含true、false; 
	 * @param message 为接口返回的个人编的提示消息;
	 */
	public void setValue(boolean state,String message) {
		setState(state);
		setMessage(message);
		setRetMessage("");
		setResult("");
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

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
