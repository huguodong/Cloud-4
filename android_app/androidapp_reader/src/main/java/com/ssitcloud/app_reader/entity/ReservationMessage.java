package com.ssitcloud.app_reader.entity;

public class ReservationMessage {
	private boolean state;
	private String message;
	private String code;//-1卡无效，-2卡密码错误，lib_not_support图书馆不支持

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
