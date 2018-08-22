package com.ssitcloud.shelfmgmt.entity;

public class BibliosEntity {

	private int bib_idx;
	private String lib_id;
	private String title;
	private String ISBN;
	private String callNo;
	private String publish;
	private String price;
	private String circulation_type;
	private String permanent_location;
	private String current_location;
	private String subject;
	private String pagenum;
	public int getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(int bib_idx) {
		this.bib_idx = bib_idx;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getCallNo() {
		return callNo;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCirculation_type() {
		return circulation_type;
	}
	public void setCirculation_type(String circulation_type) {
		this.circulation_type = circulation_type;
	}
	public String getPermanent_location() {
		return permanent_location;
	}
	public void setPermanent_location(String permanent_location) {
		this.permanent_location = permanent_location;
	}
	public String getCurrent_location() {
		return current_location;
	}
	public void setCurrent_location(String current_location) {
		this.current_location = current_location;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPagenum() {
		return pagenum;
	}
	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}
	
}
