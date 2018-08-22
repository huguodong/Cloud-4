package com.ssitcloud.mobile.entity;

public class AcsInReservationBookEntity {
	private final String operation = "lib_bookhandlecancel";
	private String book_sn;
	private String bookRecon;
	private String card_no;
	private String card_pwd;
	
	public String getBook_sn() {
		return book_sn;
	}
	public void setBook_sn(String book_sn) {
		this.book_sn = book_sn;
	}
	public String getBookRecon() {
		return bookRecon;
	}
	public void setBookRecon(String bookRecon) {
		this.bookRecon = bookRecon;
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
	public String getOperation() {
		return operation;
	}
	
}
