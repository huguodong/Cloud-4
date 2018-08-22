package com.ssitcloud.shelfmgmt.entity;

import java.util.Map;

public class BibliosEntity {
	
	private Integer bib_idx;//书目索引号
	private String ISBN;//ISBN号
	private String title;//varchar(200) NOT NULL题名
	private String author;//varchar(200) NULL作者
	private String callNo;//varchar(32) NULL索书号
	private String publish;//varchar(200) NULL出版社
	private String pubAddress;//varchar(200) NULL出版地
	private String pubDate;//varchar(32) NULL出版日期
	private String price;//varchar(32) NULL价格
	private String subject;//varchar(32) NULL主题
	private String book_page;//varchar(32) NULL页码
	private String book_size;//varchar(32) NULL规格
	private String contents;//varchar(1024) NULL简介
	private String seriesBook;//varchar(1024) NULL丛书
	private String lang;//varchar(20) NULL语种
	private String version;//varchar(20) NULL版本
	private String bookimage_url;//varchar(1024) NULL图书封面路径
	
	
	
	
	public BibliosEntity() {
		super();
	}


	public BibliosEntity(Map<String, Object> map) {
		super();
		if (map.containsKey("ISBN")) {
			this.ISBN = map.get("ISBN") + "";
		}
		if (map.containsKey("title")) {
			this.title = map.get("title") + "";
		}
		if (map.containsKey("author")) {
			this.author = map.get("author") + "";
		}
		if (map.containsKey("callNo")) {
			this.callNo = map.get("callNo") + "";
		}
		if (map.containsKey("publish")) {
			this.publish = map.get("publish") + "";
		}
		if (map.containsKey("pubAddress")) {
			this.pubAddress = map.get("pubAddress") + "";
		}
		if (map.containsKey("pubDate")) {
			this.pubDate = map.get("pubDate") + "";
		}
		if (map.containsKey("price")) {
			this.price = map.get("price") + "";
		}
		if (map.containsKey("subject")) {
			this.subject = map.get("subject") + "";
		}
		if (map.containsKey("book_page")) {
			this.book_page = map.get("book_page") + "";
		}
		if (map.containsKey("book_size")) {
			this.book_size = map.get("book_size") + "";
		}
		if (map.containsKey("contents")) {
			this.contents = map.get("contents") + "";
		}
		if (map.containsKey("seriesBook")) {
			this.seriesBook = map.get("seriesBook") + "";
		}
		if (map.containsKey("lang")) {
			this.lang = map.get("lang") + "";
		}
		if (map.containsKey("version")) {
			this.version = map.get("version") + "";
		}
		if (map.containsKey("bookimage_url")) {
			this.bookimage_url = map.get("bookimage_url") + "";
		}
	}
	
	public Integer getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(Integer bib_idx) {
		this.bib_idx = bib_idx;
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
	public String getPubAddress() {
		return pubAddress;
	}
	public void setPubAddress(String pubAddress) {
		this.pubAddress = pubAddress;
	}
	public String getPubDate() {
		return pubDate;
	}
	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBook_page() {
		return book_page;
	}
	public void setBook_page(String book_page) {
		this.book_page = book_page;
	}
	public String getBook_size() {
		return book_size;
	}
	public void setBook_size(String book_size) {
		this.book_size = book_size;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getSeriesBook() {
		return seriesBook;
	}
	public void setSeriesBook(String seriesBook) {
		this.seriesBook = seriesBook;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBookimage_url() {
		return bookimage_url;
	}
	public void setBookimage_url(String bookimage_url) {
		this.bookimage_url = bookimage_url;
	}
	
}
