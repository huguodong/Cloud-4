package com.ssitcloud.devicelog.entity;

import java.io.Serializable;

public class BookrackStateDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4688179692374606934L;
	// 书架号
	private String location;
	// 状态
	private String state;
	// 书本条码号
	private String barcode;
	// 书名
	private String title;
	// 作者
	private String author;
	// 索书号
	private String callno;
	// 预借读者卡号
	private String cardno;
	// 上架时间
	private String itemloandate;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCallno() {
		return callno;
	}

	public void setCallno(String callno) {
		this.callno = callno;
	}

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getItemloandate() {
		return itemloandate;
	}

	public void setItemloandate(String itemloandate) {
		this.itemloandate = itemloandate;
	}
}
