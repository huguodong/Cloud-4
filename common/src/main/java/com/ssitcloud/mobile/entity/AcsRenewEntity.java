package com.ssitcloud.mobile.entity;

public class AcsRenewEntity {
	private final String operation = "lib_renew";
	private String book_sn;
	private String card_no;
	private String card_pwd;
	
	public String getBook_sn() {
		return book_sn;
	}
	public void setBook_sn(String book_sn) {
		this.book_sn = book_sn;
	}
	public String getOperation() {
		return operation;
	}
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
}
