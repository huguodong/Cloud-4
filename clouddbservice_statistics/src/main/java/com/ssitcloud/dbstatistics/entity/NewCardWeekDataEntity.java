package com.ssitcloud.dbstatistics.entity;

/**
 * 办证查询按周统计实体
 * author huanghuang
 * 2017年2月13日 上午10:36:34
 */
public class NewCardWeekDataEntity{
	/**
	 * 
	 */
	private Integer newCard_idx;//int(11) NOT NULL 数据idx
	private Integer lib_idx;//int(11) NOT NULL 图书馆ID
	private Integer device_idx;//设备ID
	private String newCardCountType;//varchar(3) NULL 统计类型
	private String newCardsubType;//varchar(10) NULL 统计子类
	private Integer newCardWeekCount;//int(11) NULL 周统计结果
	private String newCardDate;//varchar(8) NULL 统计日期
	private String createDate;//varchar(8) NULL 创建日期
	private String updateDate;//varchar(8) NULL 修改日期
	
	public Integer getNewCard_idx() {
		return newCard_idx;
	}
	public void setNewCard_idx(Integer newCard_idx) {
		this.newCard_idx = newCard_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
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
	public Integer getNewCardWeekCount() {
		return newCardWeekCount;
	}
	public void setNewCardWeekCount(Integer newCardWeekCount) {
		this.newCardWeekCount = newCardWeekCount;
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
	
	
}
