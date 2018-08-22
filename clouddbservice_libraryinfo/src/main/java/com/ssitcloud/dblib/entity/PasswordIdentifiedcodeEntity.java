package com.ssitcloud.dblib.entity;

import java.sql.Timestamp;

public class PasswordIdentifiedcodeEntity {
	private Integer reader_idx;
	private String identifying_code;
	private Timestamp createtime;
	
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
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
