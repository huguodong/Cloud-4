package com.ssitcloud.dblib.entity;

public class BibliosAndBook{
	private Integer bib_idx;//书目索引号
	private String ISBN;//ISBN号
	private String title;//varchar(200) NOT NULL题名
	private String author;//varchar(200) NULL作者
	private String classNo;//varchar(32) NULL分类号
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
	
	private Integer bookitem_idx;//int(11) NOT NULL
	private Integer lib_idx;//所属馆图书馆IDx
	private Integer nowlib_idx;//当前馆idx
	private Integer serverlib_idx;//服务馆idx
	private String book_barcode;//varchar(32) NOT NULL图书条码
	private String book_uid;//varchar(50) NULL图书UID
	private String shelflayer_barcode;//varchar(32) NULL层架标号
	private Integer update_uid_flag;//int(1) NULL是否更新过标签，1否，2是
	private Integer state;//int(1) NULL图书状态
	private String updatetime;//varchar(32) NULL最近一次状态变更时间
	private Integer stateModCount;//int(11) NULL状态变更次数
	private String location;//馆藏地点
	private String nowlocation;//当前馆藏地
	private Integer device_idx;//设备idx
	private Integer booktype_idx;//图书类型idx
	private Integer mediatype_idx;//载体类型idx
	private String createtime;//创建时间
	private String regdate;//入档时间
	private String callNo;//索书号
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
	public Integer getStateModCount() {
		return stateModCount;
	}
	public void setStateModCount(Integer stateModCount) {
		this.stateModCount = stateModCount;
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
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}
	public Integer getBooktype_idx() {
		return booktype_idx;
	}
	public void setBooktype_idx(Integer booktype_idx) {
		this.booktype_idx = booktype_idx;
	}
	public Integer getMediatype_idx() {
		return mediatype_idx;
	}
	public void setMediatype_idx(Integer mediatype_idx) {
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
	public String getCallNo() {
		return callNo;
	}
	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}
}
