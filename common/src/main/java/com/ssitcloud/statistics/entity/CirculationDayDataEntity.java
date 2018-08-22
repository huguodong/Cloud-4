package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

public class CirculationDayDataEntity {
	
	@JestId
	private String cir_idx;//数据idx
	
	private String lib_idx;//图书馆ID
	
	private String device_idx;//设备ID
	
	private String circulateCountType;//统计类型
	
	private String cirsubType;//统计子类
	
	private Integer loan_cirDayCount;//借书日统计结果
	
	private Integer ruturn_cirDayCount;//还书日统计结果
	
	private Integer renew_cirDayCount;//续借日统计结果
	
	private String circulateDate;//统计日期
	
	private String createDate;//创建日期
	
	private String updateDate;//修改日期
	
	private String cirWeek;//星期几
	
	
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

	
	
	
	
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
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
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
}
