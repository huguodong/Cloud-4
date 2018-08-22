package com.ssitcloud.shelfmgmt.entity;


public class BookitemEntity {
	
	
	private String book_barcode;
	private String book_uid;
	private String ISBN;
	private String title;
	private String author;
	private String publish;
	private String callNo;
	private String shelflayer_barcode;
	private Integer update_uid_flag;
	private Integer state;
	private String updatetime;
	private Integer statemodcount;
	private String location;
	private String subsidiary;
	private String device_id;
	private String uploadtime;
	
	public BookitemEntity() {
		super();
	}

	public String getBook_barcode() {
		return book_barcode;
	}


	public void setBook_barcode(String book_barcode) {
		this.book_barcode = book_barcode;
	}


	public String getBook_uid() {
		return book_uid;
	}


	public void setBook_uid(String book_uid) {
		this.book_uid = book_uid;
	}


	public String getISBN() {
		return ISBN;
	}


	public void setISBN(String iSBN) {
		ISBN = iSBN;
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


	public String getPublish() {
		return publish;
	}


	public void setPublish(String publish) {
		this.publish = publish;
	}


	public String getCallNo() {
		return callNo;
	}


	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}


	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}


	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}


	public Integer getUpdate_uid_flag() {
		return update_uid_flag;
	}


	public void setUpdate_uid_flag(Integer update_uid_flag) {
		this.update_uid_flag = update_uid_flag;
	}


	public Integer getState() {
		return state;
	}


	public void setState(Integer state) {
		this.state = state;
	}


	public String getUpdatetime() {
		return updatetime;
	}


	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}


	public Integer getStatemodcount() {
		return statemodcount;
	}


	public void setStatemodcount(Integer statemodcount) {
		this.statemodcount = statemodcount;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getSubsidiary() {
		return subsidiary;
	}


	public void setSubsidiary(String subsidiary) {
		this.subsidiary = subsidiary;
	}


	public String getDevice_id() {
		return device_id;
	}


	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

	
}
