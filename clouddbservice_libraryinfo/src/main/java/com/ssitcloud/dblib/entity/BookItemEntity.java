package com.ssitcloud.dblib.entity;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class BookItemEntity {
	
	private Integer bookitem_idx;//int(11) NOT NULL
	private Integer lib_idx;//所属馆图书馆IDx
	private Integer nowlib_idx;//当前馆idx
	private Integer serverlib_idx;//服务馆idx
	private String book_barcode;//varchar(32) NOT NULL图书条码
	private String book_uid;//varchar(50) NULL图书UID
	private Integer bib_idx;//书目记录号
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
	private String creatime;//创建时间
	private String regdate;//入档时间
	private String callNo;//索书号
	
	
	//一下几个字段用于保存biblios的信息，用来查询bib_idx
	private String ISBN;
	private String title;
	private String author;
	private String publish;
	private String pubDate;
	
	private boolean isNewArrival;//是否是新书（pubDate在一个礼拜内的定义为新书）
	
	
	
	
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


	public String getPubDate() {
		return pubDate;
	}


	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
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


	public String getCreatime() {
		return creatime;
	}


	public void setCreatime(String creatime) {
		this.creatime = creatime;
	}


	public String getRegdate() {
		return regdate;
	}


	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}


	public BookItemEntity() {
		super();
	}

	
	public BookItemEntity(Map<String, Object> map) {
		super();
		
		if (map.containsKey("book_barcode")) {
			this.book_barcode = map.get("book_barcode") + "";
		}
		if (map.containsKey("book_uid")) {
			this.book_uid = map.get("book_uid") + "";
		}
		if (map.containsKey("shelflayer_barcode")) {
			this.shelflayer_barcode = map.get("shelflayer_barcode") + "";
		}
		if (map.containsKey("update_uid_flag")) {
			String temp = map.get("update_uid_flag") + "";
			if (!StringUtils.isEmpty(temp)&&StringUtils.isNumeric(temp)) {
				this.update_uid_flag = Integer.valueOf(temp);
			}
		}
		if (map.containsKey("state")) {
			String temp = map.get("state") + "";
			if (!StringUtils.isEmpty(temp)&&StringUtils.isNumeric(temp)) {
				this.state = Integer.valueOf(temp);
			}
		}
		if (map.containsKey("updatetime")) {
			this.updatetime = map.get("updatetime") + "";
		}
		if (map.containsKey("statemodcount")) {
			String temp = map.get("statemodcount") + "";
			if (!StringUtils.isEmpty(temp)&&StringUtils.isNumeric(temp)) {
				this.stateModCount = Integer.valueOf(temp);
			}
		}
		if (map.containsKey("location")) {
			this.location = map.get("location") + "";
		}
		if (map.containsKey("nowlocation")) {
			this.nowlocation = map.get("nowlocation") + "";
		}
		if (map.containsKey("callNo")) {
			this.callNo = map.get("callNo") + "";
		}
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
	public Integer getBib_idx() {
		return bib_idx;
	}
	public void setBib_idx(Integer bib_idx) {
		this.bib_idx = bib_idx;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
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

	public String getNowlocation() {
		return nowlocation;
	}

	public void setNowlocation(String nowlocation) {
		this.nowlocation = nowlocation;
	}
	


	public String getCallNo() {
		return callNo;
	}


	public void setCallNo(String callNo) {
		this.callNo = callNo;
	}


	public boolean isNewArrival() {
		return isNewArrival;
	}


	public void setNewArrival(boolean isNewArrival) {
		this.isNewArrival = isNewArrival;
	}


	@Override
	public int hashCode() {
		if (book_barcode!=null && !"".equals(book_barcode)) {
			return book_barcode.hashCode();
		}else{
			return 0;
		}
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) return false;
		
		return (this.hashCode() == obj.hashCode());
	}
	
	
	
	
}
