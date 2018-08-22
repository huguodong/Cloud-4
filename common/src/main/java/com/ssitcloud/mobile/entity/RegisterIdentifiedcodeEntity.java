package com.ssitcloud.mobile.entity;

import java.sql.Timestamp;

public class RegisterIdentifiedcodeEntity {
	private String email_address;
	private String identifying_code;
	private Timestamp createtime;
	
	public String getEmail_address() {
		return email_address;
	}
	public void setEmail_address(String email_address) {
		this.email_address = email_address;
	}
	public String getIdentifying_code() {
		return identifying_code;
	}
	public void setIdentifying_code(String identifying_code) {
		this.identifying_code = identifying_code;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
}
