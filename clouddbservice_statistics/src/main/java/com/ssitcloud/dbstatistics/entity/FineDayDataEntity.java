package com.ssitcloud.dbstatistics.entity;

public class FineDayDataEntity {
	private Integer fine_idx;//数据idx
	private Integer lib_idx;//图书馆ID
	private Integer device_idx;//设备ID
	private String fineCountType;//统计类型
	private String finesubType;//统计子类
	private Integer fineDayCount;//日统计结果
	private String fineDate;//统计日期
	private String createDate;//创建日期
	private String updateDate;//修改日期
	private String fineWeek;//星期几
	public Integer getFine_idx() {
		return fine_idx;
	}
	public void setFine_idx(Integer fine_idx) {
		this.fine_idx = fine_idx;
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
	public String getFineCountType() {
		return fineCountType;
	}
	public void setFineCountType(String fineCountType) {
		this.fineCountType = fineCountType;
	}
	public String getFinesubType() {
		return finesubType;
	}
	public void setFinesubType(String finesubType) {
		this.finesubType = finesubType;
	}
	public Integer getFineDayCount() {
		return fineDayCount;
	}
	public void setFineDayCount(Integer fineDayCount) {
		this.fineDayCount = fineDayCount;
	}
	public String getFineDate() {
		return fineDate;
	}
	public void setFineDate(String fineDate) {
		this.fineDate = fineDate;
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
	public String getFineWeek() {
		return fineWeek;
	}
	public void setFineWeek(String fineWeek) {
		this.fineWeek = fineWeek;
	}
	
	
	
}
