package com.ssitcloud.dbstatistics.entity;

public class CirculationMonthDataEntity {
	private Integer cir_idx;//数据idx
	private Integer lib_idx;//图书馆ID
	private Integer device_idx;//设备ID
	private String circulateCountType;//统计类型
	private String cirsubType;//统计子类
	private Integer loan_cirMonthCount;//借书月统计结果
	private Integer return_cirMonthCount;//还书月统计结果
	private Integer renew_cirMonthCount;//续借月统计结果
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
	
	public Integer getLoan_cirMonthCount() {
		return loan_cirMonthCount;
	}
	public void setLoan_cirMonthCount(Integer loan_cirMonthCount) {
		this.loan_cirMonthCount = loan_cirMonthCount;
	}
	public Integer getReturn_cirMonthCount() {
		return return_cirMonthCount;
	}
	public void setReturn_cirMonthCount(Integer return_cirMonthCount) {
		this.return_cirMonthCount = return_cirMonthCount;
	}
	public Integer getRenew_cirMonthCount() {
		return renew_cirMonthCount;
	}
	public void setRenew_cirMonthCount(Integer renew_cirMonthCount) {
		this.renew_cirMonthCount = renew_cirMonthCount;
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
