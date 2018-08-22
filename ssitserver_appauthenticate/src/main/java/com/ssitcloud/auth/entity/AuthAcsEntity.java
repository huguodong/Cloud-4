package com.ssitcloud.auth.entity;


/**
 * 
 * @comment 表名AuthAcsEntity
 * 
 * @author min
 * @data 2017年4月25日
 */
public class AuthAcsEntity{
	private String type;
	private String device_id;
	private String reader_code;
	private String md5;
	private String receive_date;
	private String state;

	public AuthAcsEntity() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getReader_code() {
		return reader_code;
	}

	public void setReader_code(String reader_code) {
		this.reader_code = reader_code;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}


	public String getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AuthAcsEntity [type=" + type + ", device_id=" + device_id
				+ ", reader_code=" + reader_code + ", md5=" + md5
				+ ", receive_date=" + receive_date + ", state=" + state + "]";
	}

}
