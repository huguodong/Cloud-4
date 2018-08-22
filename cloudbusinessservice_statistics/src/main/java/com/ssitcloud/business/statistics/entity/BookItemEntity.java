package com.ssitcloud.business.statistics.entity;

import io.searchbox.annotations.JestId;

public class BookItemEntity {

	
	@JestId
	private String bookitem_idx; //bookitem表中的idx
	
	private String book_barcode;//图书条码马号
	private String book_uid;//图书标签uid
	private String callNo;//索取号
	private String shelflayer_barcode;//层架标号
	private String state;//图书状态0 – 已分派1 – 已上架2 – 已借出3 – 已下架
	private String updatetime;//最近一次状态变更时间
	private String statemodcount;//状态变更次数
	private String location;//馆藏地点
	private String nowlocation;//当前馆藏地点
	
	private String booktype_idx;//图书类型idx
	private String mediatype_idx;//载体类型idx
	private String createtime;//创建时间
	private String regdate;//入档段时间
	private String isbn;//isbn号
	private String title;//标题
	private String author;//著者
	private String classNo;//分类号
	private String publish;//出版社
	private String pubAddress;//出版地址
	private String pubDate;//出版日期
	private String price;//价格
	private String subject;//主题词
	private String book_page;//图书页码
	private String book_size;//规格
	private String contents;//简介
	private String seriesBook;//丛书
	private String lang;//语言
	private String version;//版本
	private String bookimage_url;//图片地址
	
	/** 图书馆相关参数 */
	private String lib_idx;//图书馆idx
	private String lib_name;
	private String lib_id;
	private String library_idx ;//图书馆idx
	
	private String nowlib_idx;//当前所在馆的idx
	private String nowlib_name;//当前馆名称
	private String nowlib_id;//id
	
	private String serverlib_idx;//服务馆的idx
	private String serverlib_name;
	private String serverlib_id;
	
	/**  设备相关的参数 **/
	private String device_idx;
	private String device_id;//设备id
	private String device_name;//设备名称
	private String device_type;//设备类型
	private String device_type_desc;//设备类型描述
	private String device_type_mark;
	private String areaCode;
	
	
	public String getBookitem_idx() {
		return bookitem_idx;
	}
	public void setBookitem_idx(String bookitem_idx) {
		this.bookitem_idx = bookitem_idx;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getStatemodcount() {
		return statemodcount;
	}
	public void setStatemodcount(String statemodcount) {
		this.statemodcount = statemodcount;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getNowlocation() {
		return nowlocation;
	}
	public void setNowlocation(String nowlocation) {
		this.nowlocation = nowlocation;
	}
	public String getBooktype_idx() {
		return booktype_idx;
	}
	public void setBooktype_idx(String booktype_idx) {
		this.booktype_idx = booktype_idx;
	}
	public String getMediatype_idx() {
		return mediatype_idx;
	}
	public void setMediatype_idx(String mediatype_idx) {
		this.mediatype_idx = mediatype_idx;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
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
	public String getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(String lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getLib_name() {
		return lib_name;
	}
	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getNowlib_idx() {
		return nowlib_idx;
	}
	public void setNowlib_idx(String nowlib_idx) {
		this.nowlib_idx = nowlib_idx;
	}
	public String getNowlib_name() {
		return nowlib_name;
	}
	public void setNowlib_name(String nowlib_name) {
		this.nowlib_name = nowlib_name;
	}
	public String getNowlib_id() {
		return nowlib_id;
	}
	public void setNowlib_id(String nowlib_id) {
		this.nowlib_id = nowlib_id;
	}
	public String getServerlib_idx() {
		return serverlib_idx;
	}
	public void setServerlib_idx(String serverlib_idx) {
		this.serverlib_idx = serverlib_idx;
	}
	public String getServerlib_name() {
		return serverlib_name;
	}
	public void setServerlib_name(String serverlib_name) {
		this.serverlib_name = serverlib_name;
	}
	public String getServerlib_id() {
		return serverlib_id;
	}
	public void setServerlib_id(String serverlib_id) {
		this.serverlib_id = serverlib_id;
	}
	public String getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(String device_idx) {
		this.device_idx = device_idx;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getDevice_type_desc() {
		return device_type_desc;
	}
	public void setDevice_type_desc(String device_type_desc) {
		this.device_type_desc = device_type_desc;
	}
	public String getDevice_type_mark() {
		return device_type_mark;
	}
	public void setDevice_type_mark(String device_type_mark) {
		this.device_type_mark = device_type_mark;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	
}
