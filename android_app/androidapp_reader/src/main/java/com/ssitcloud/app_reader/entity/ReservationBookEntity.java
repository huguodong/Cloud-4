package com.ssitcloud.app_reader.entity;

public class ReservationBookEntity extends BibliosEntity{
	private String book_barcode;//条码号
	private String bookRecode;//书目记录号
	private String logisticsNum;//物流编码
	private String deliverAddr;//投放地点
	private String deadline;//预借截止日期

	public String getDeliverAddr() {
		return deliverAddr;
	}
	public void setDeliverAddr(String deliverAddr) {
		this.deliverAddr = deliverAddr;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getBook_barcode() {
		return book_barcode;
	}
	public void setBook_barcode(String book_barcode) {
		this.book_barcode = book_barcode;
	}
	public String getBookRecode() {
		return bookRecode;
	}
	public void setBookRecode(String bookRecode) {
		this.bookRecode = bookRecode;
	}
	public String getLogisticsNum() {
		return logisticsNum;
	}
	public void setLogisticsNum(String logisticsNum) {
		this.logisticsNum = logisticsNum;
	}
}
