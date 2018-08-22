package com.ssitcloud.mobile.entity;

/**
 * 预约借书acs指令实体
 * @author LXP
 *
 */
public class AcsReservationBookEntity {
	/**
	 * 通知方式
	 */
	public enum NOTIFY_MODE{EMAIL/*电子邮件*/,NETWORK_ENQUITY/*网上查询*/,PHONE/*电话*/,LETTER/*信函*/}
	
	private final String operation = "lib_bookhandle";
	private String book_sn;
	private String card_no;
	private String card_pwd;
	private NOTIFY_MODE notifyModel;
	private String bookHandleAddr;
	
	public String getBook_sn() {
		return book_sn;
	}
	public void setBook_sn(String book_sn) {
		this.book_sn = book_sn;
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
	public NOTIFY_MODE getNotifyModel() {
		return notifyModel;
	}
	public void setNotifyModel(NOTIFY_MODE notifyModel) {
		this.notifyModel = notifyModel;
	}
	public String getOperation() {
		return operation;
	}
	public String getBookHandleAddr() {
		return bookHandleAddr;
	}
	public void setBookHandleAddr(String bookHandleAddr) {
		this.bookHandleAddr = bookHandleAddr;
	}
	
}
