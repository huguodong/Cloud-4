package com.ssitcloud.libraryinfo.entity;

public class BibliosEntity {

	private int bib_idx;
	private String ISBN;
	private String title;
	private String author;
	private String classNo;
	private String publish;
	private String pubAddress;
	private String pubDate;
	private String price;
	private String subject;
	private String book_page;
	private String book_size;
	private String contents;
	private String seriesBook;
	private String lang;
	private String version;
	private String bookimage_url;
	public int getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(int bib_idx) {
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
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
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
	@Override
	public String toString() {
		return "BibliosEntity [bib_idx=" + bib_idx + ", ISBN=" + ISBN
				+ ", title=" + title + ", author=" + author + ", callNo="
				+ classNo + ", publish=" + publish + ", pubAddress="
				+ pubAddress + ", pubDate=" + pubDate + ", price=" + price
				+ ", subject=" + subject + ", book_page=" + book_page
				+ ", book_size=" + book_size + ", contents=" + contents
				+ ", seriesBook=" + seriesBook + ", lang=" + lang
				+ ", version=" + version + ", bookimage_url=" + bookimage_url
				+ "]";
	}
	
	
	
}
