package com.ssitcloud.mobile.entity;

public class EmailEntity {
	private Integer lib_idx;
	private String receEmailAddr;
	private String subject;
	private String content;
	
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getReceEmailAddr() {
		return receEmailAddr;
	}
	public void setReceEmailAddr(String receEmailAddr) {
		this.receEmailAddr = receEmailAddr;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
