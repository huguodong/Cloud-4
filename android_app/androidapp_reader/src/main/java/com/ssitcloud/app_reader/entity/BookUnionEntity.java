package com.ssitcloud.app_reader.entity;

import java.io.Serializable;

public class BookUnionEntity implements Serializable{
	private Integer bookitem_idx;//int(11) NOT NULL
	private Integer lib_idx;//所属馆图书馆IDx
	private Integer nowlib_idx;//当前馆idx
	private Integer serverlib_idx;//服务馆idx
	private String book_barcode;//varchar(32) NOT NULL图书条码
	private String book_uid;//varchar(50) NULL图书UID	
	private String shelflayer_barcode;//varchar(32) NULL层架标号
	private Integer state;//int(1) NULL图书状态
	private String location;//馆藏地点
	private String noewlocation;//当前馆藏地
	private Integer device_idx;//设备idx
	private Integer bib_idx;//书目记录号
	private String bookimage_url;//varchar(1024) NULL图书封面路径
	private String ISBN;//ISBN号
	private String title;//varchar(200) NOT NULL题名
	private String author;//varchar(200) NULL作者
	private String publish;//varchar(200) NULL出版社
	private String subject;//varchar(32) NULL主题
	public Integer getBookitem_idx() {
		return bookitem_idx;
	}
	public void setBookitem_idx(Integer bookitem_idx) {
		this.bookitem_idx = bookitem_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public Integer getNowlib_idx() {
		return nowlib_idx;
	}
	public void setNowlib_idx(Integer nowlib_idx) {
		this.nowlib_idx = nowlib_idx;
	}
	public Integer getServerlib_idx() {
		return serverlib_idx;
	}
	public void setServerlib_idx(Integer serverlib_idx) {
		this.serverlib_idx = serverlib_idx;
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
	public String getShelflayer_barcode() {
		return shelflayer_barcode;
	}
	public void setShelflayer_barcode(String shelflayer_barcode) {
		this.shelflayer_barcode = shelflayer_barcode;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNoewlocation() {
		return noewlocation;
	}
	public void setNoewlocation(String noewlocation) {
		this.noewlocation = noewlocation;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}
	public Integer getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(Integer bib_idx) {
		this.bib_idx = bib_idx;
	}
	public String getBookimage_url() {
		return bookimage_url;
	}
	public void setBookimage_url(String bookimage_url) {
		this.bookimage_url = bookimage_url;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
