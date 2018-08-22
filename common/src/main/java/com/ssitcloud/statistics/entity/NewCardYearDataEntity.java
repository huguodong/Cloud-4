package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

/**
 * 办证查询按年统计实体
 * author huanghuang
 * 2017年2月13日 上午10:36:34
 */
public class NewCardYearDataEntity{
	/**
	 * 
	 */
	@JestId
	private String newCard_idx;//int(11) NOT NULL 数据idx
	private String lib_idx;//int(11) NOT NULL 图书馆ID
	private String device_idx;//设备ID
	private String newCardCountType;//varchar(3) NULL 统计类型
	private String newCardsubType;//varchar(10) NULL 统计子类
	private Integer newCardYearCount;//int(11) NULL 年统计结果
	private String newCardDate;//varchar(8) NULL 统计日期
	private String createDate;//varchar(8) NULL 创建日期
	private String updateDate;//varchar(8) NULL 修改日期
	
	/** 图书馆相关参数 */
	private String lib_name;
	private String lib_id;
	
	/**  设备相关的参数 **/
	private String device_id;
	private String device_name;
	private String device_type;
	private String device_type_desc;
	private String device_type_mark;
	private String areaCode;
	
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
	public String getNewCard_idx() {
		return newCard_idx;
	}
	public void setNewCard_idx(String newCard_idx) {
		this.newCard_idx = newCard_idx;
	}
	public String getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(String lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(String device_idx) {
		this.device_idx = device_idx;
	}
	public String getNewCardCountType() {
		return newCardCountType;
	}
	public void setNewCardCountType(String newCardCountType) {
		this.newCardCountType = newCardCountType;
	}
	public String getNewCardsubType() {
		return newCardsubType;
	}
	public void setNewCardsubType(String newCardsubType) {
		this.newCardsubType = newCardsubType;
	}
	public Integer getNewCardYearCount() {
		return newCardYearCount;
	}
	public void setNewCardYearCount(Integer newCardYearCount) {
		this.newCardYearCount = newCardYearCount;
	}
	public String getNewCardDate() {
		return newCardDate;
	}
	public void setNewCardDate(String newCardDate) {
		this.newCardDate = newCardDate;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
}
