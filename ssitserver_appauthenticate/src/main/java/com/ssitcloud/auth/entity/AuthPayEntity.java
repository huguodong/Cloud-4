package com.ssitcloud.auth.entity;


/**
 * 
 * @comment 表名AuthEntity
 * 
 * @author min
 * @data 2017年4月25日
 */
public class AuthPayEntity{
	private String random_code;
	private String scan_date;
	private String device_id;
	private String reader_code;
	private String reader_passwd;
	private String md5;
	private String state;

	public AuthPayEntity() {
	}

	public String getRandom_code() {
		return random_code;
	}

	public void setRandom_code(String random_code) {
		this.random_code = random_code;
	}

	public String getScan_date() {
		return scan_date;
	}

	public void setScan_date(String scan_date) {
		this.scan_date = scan_date;
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

	public String getReader_passwd() {
		return reader_passwd;
	}

	public void setReader_passwd(String reader_passwd) {
		this.reader_passwd = reader_passwd;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "AuthPayEntity [random_code=" + random_code + ", scan_date="
				+ scan_date + ", device_id=" + device_id + ", reader_code="
				+ reader_code + ", reader_passwd=" + reader_passwd + ", md5="
				+ md5 + ", state=" + state + "]";
	}

}
