package com.ssitcloud.mobile.entity;

public class AcsBookInfoByReconEntity {
	private final String operation = "lib_querybiblios";
	private String bookRecon;

	public String getBookRecon() {
		return bookRecon;
	}

	public void setBookRecon(String bookRecon) {
		this.bookRecon = bookRecon;
	}

	public String getOperation() {
		return operation;
	}
}
