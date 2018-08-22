package com.ssitcloud.dbstatistics.entity;

public class CirculationYearDataEntity {
	private Integer cir_idx;//数据idx
	private Integer lib_idx;//图书馆ID
	private Integer device_idx;//设备ID
	private String circulateCountType;//统计类型
	private String cirsubType;//统计子类
	private Integer loan_cirYearCount;//借书年统计结果
	private Integer return_cirYearCount;//还书年统计结果
	private Integer renew_cirYearCount;//续借年统计结果
	private String circulateDate;//统计日期
	private String createDate;//创建日期
	private String updateDate;//修改日期
	
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
	
	public Integer getLoan_cirYearCount() {
		return loan_cirYearCount;
	}
	public void setLoan_cirYearCount(Integer loan_cirYearCount) {
		this.loan_cirYearCount = loan_cirYearCount;
	}
	public Integer getReturn_cirYearCount() {
		return return_cirYearCount;
	}
	public void setReturn_cirYearCount(Integer return_cirYearCount) {
		this.return_cirYearCount = return_cirYearCount;
	}
	public Integer getRenew_cirYearCount() {
		return renew_cirYearCount;
	}
	public void setRenew_cirYearCount(Integer renew_cirYearCount) {
		this.renew_cirYearCount = renew_cirYearCount;
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
	
	
}
