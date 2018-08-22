package com.ssitcloud.dbstatistics.entity;

public class CirculationDayDataEntity {
	private Integer cir_idx;//数据idx
	private Integer lib_idx;//图书馆ID
	private Integer device_idx;//设备ID
	private String circulateCountType;//统计类型
	private String cirsubType;//统计子类
	private Integer loan_cirDayCount;//借书日统计结果
	private Integer ruturn_cirDayCount;//还书日统计结果
	private Integer renew_cirDayCount;//续借日统计结果
	private String circulateDate;//统计日期
	private String createDate;//创建日期
	private String updateDate;//修改日期
	private String cirWeek;//星期几
	
	public Integer getCir_idx() {
		return cir_idx;
	}
	public void setCir_idx(Integer cir_idx) {
		this.cir_idx = cir_idx;
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
	public String getCirculateCountType() {
		return circulateCountType;
	}
	public void setCirculateCountType(String circulateCountType) {
		this.circulateCountType = circulateCountType;
	}
	public String getCirsubType() {
		return cirsubType;
	}
	public void setCirsubType(String cirsubType) {
		this.cirsubType = cirsubType;
	}
	
	public Integer getLoan_cirDayCount() {
		return loan_cirDayCount;
	}
	public void setLoan_cirDayCount(Integer loan_cirDayCount) {
		this.loan_cirDayCount = loan_cirDayCount;
	}
	public Integer getRuturn_cirDayCount() {
		return ruturn_cirDayCount;
	}
	public void setRuturn_cirDayCount(Integer ruturn_cirDayCount) {
		this.ruturn_cirDayCount = ruturn_cirDayCount;
	}
	public Integer getRenew_cirDayCount() {
		return renew_cirDayCount;
	}
	public void setRenew_cirDayCount(Integer renew_cirDayCount) {
		this.renew_cirDayCount = renew_cirDayCount;
	}
	public String getCirculateDate() {
		return circulateDate;
	}
	public void setCirculateDate(String circulateDate) {
		this.circulateDate = circulateDate;
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
	public String getCirWeek() {
		return cirWeek;
	}
	public void setCirWeek(String cirWeek) {
		this.cirWeek = cirWeek;
	}
	
	
	
}
