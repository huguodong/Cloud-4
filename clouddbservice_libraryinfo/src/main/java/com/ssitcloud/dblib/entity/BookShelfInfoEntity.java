package com.ssitcloud.dblib.entity;

import java.util.Date;

public class BookShelfInfoEntity {
	private String lib_id;//图书馆ID
	private String shelf_id;//书架ID
	private Integer info_type;//书架位置索引
	private String info_value;//书架信息取值
	private Date updatetime;
	
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getShelf_id() {
		return shelf_id;
	}
	public void setShelf_id(String shelf_id) {
		this.shelf_id = shelf_id;
	}
	public Integer getInfo_type() {
		return info_type;
	}
	public void setInfo_type(Integer info_type) {
		this.info_type = info_type;
	}
	public String getInfo_value() {
		return info_value;
	}
	public void setInfo_value(String info_value) {
		this.info_value = info_value;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	

}
