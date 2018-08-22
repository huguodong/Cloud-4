package com.ssitcloud.mobile.entity;

public class AcsReaderCardEntity {
	private final String operation = "lib_querypatroninfo";
	
	private String card_no;
	private String card_pwd;
	
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getCard_pwd() {
		return card_pwd;
	}
	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}
	public String getOperation() {
		return operation;
	}
}
