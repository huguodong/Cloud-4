package com.ssitcloud.shelfmgmt.entity;

public class BookitemEntity {

	private int bookitem_idx;
	private String lib_id;
	private String book_barcode;
	private String book_uid;
	private int bib_idx;
	private String shelflayer_barcode;
	private int update_uid_flag;
	private int state;
	private String updatetime;
	private int statemodcount;
	private String location;
	private int device_idx;
	private String title;
	public int getBookitem_idx() {
		return bookitem_idx;
	}
	public void setBookitem_idx(int bookitem_idx) {
		this.bookitem_idx = bookitem_idx;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
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
	public int getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(int bib_idx) {
		this.bib_idx = bib_idx;
	}
	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}
	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}
	public int getUpdate_uid_flag() {
		return update_uid_flag;
	}
	public void setUpdate_uid_flag(int update_uid_flag) {
		this.update_uid_flag = update_uid_flag;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public int getStatemodcount() {
		return statemodcount;
	}
	public void setStatemodcount(int statemodcount) {
		this.statemodcount = statemodcount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(int device_idx) {
		this.device_idx = device_idx;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "BookitemEntity [bookitem_idx=" + bookitem_idx + ", lib_id="
				+ lib_id + ", book_barcode=" + book_barcode + ", book_uid="
				+ book_uid + ", bib_idx=" + bib_idx + ", shelflayer_barcode="
				+ shelflayer_barcode + ", update_uid_flag=" + update_uid_flag
				+ ", state=" + state + ", updatetime=" + updatetime
				+ ", statemodcount=" + statemodcount + ", location=" + location
				+ ", device_idx=" + device_idx + ", title=" + title + "]";
	}
	
}
