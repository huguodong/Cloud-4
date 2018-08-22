package com.ssitcloud.mobile.entity;

public class AcsBookInfoEntity {
	private final String operation = "lib_querybookinfo";
	private String book_sn;
	
	public String getBook_sn() {
		return book_sn;
	}
	public void setBook_sn(String book_sn) {
		this.book_sn = book_sn;
	}
	public String getOperation() {
		return operation;
	}
}
