package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

public class CirculationMonthDataEntity {
	
	@JestId
	private String cir_idx;//数据idx
	
	private String lib_idx;//图书馆ID
	
	private String device_idx;//设备ID
	
	private String circulateCountType;//统计类型
	
	private String cirsubType;//统计子类
	
	private Integer loan_cirMonthCount;//借书月统计结果
	
	private Integer return_cirMonthCount;//还书月统计结果
	
	private Integer renew_cirMonthCount;//续借月统计结果
	
	private String circulateDate;//统计日期
	
	private String createDate;//创建日期
	
	private String updateDate;//修改日期
	
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
	public String getCir_idx() {
		return cir_idx;
	}
	public void setCir_idx(String cir_idx) {
		this.cir_idx = cir_idx;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	
	
}
