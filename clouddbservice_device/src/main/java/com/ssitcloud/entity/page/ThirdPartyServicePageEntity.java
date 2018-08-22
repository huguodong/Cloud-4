package com.ssitcloud.entity.page;

import java.util.Date;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class ThirdPartyServicePageEntity extends DatagridPageEntity{
	private static final long serialVersionUID = -5090920989820458693L;
	private Integer service_idx;
	private String app_key;
	private String service_name;
	private String service_desc;
	private Integer library_idx;
	private String request_ip;
	private Integer access_times;
	private Integer isDisable;
	private Date operate_time;
	
	public Integer getService_idx() {
		return service_idx;
	}
	public void setService_idx(Integer service_idx) {
		this.service_idx = service_idx;
	}
	public String getApp_key() {
		return app_key;
	}
	public void setApp_key(String app_key) {
		this.app_key = app_key;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getService_desc() {
		return service_desc;
	}
	public void setService_desc(String service_desc) {
		this.service_desc = service_desc;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public String getRequest_ip() {
		return request_ip;
	}
	public void setRequest_ip(String request_ip) {
		this.request_ip = request_ip;
	}
	public Integer getAccess_times() {
		return access_times;
	}
	public void setAccess_times(Integer access_times) {
		this.access_times = access_times;
	}
	public Integer getIsDisable() {
		return isDisable;
	}
	public void setIsDisable(Integer isDisable) {
		this.isDisable = isDisable;
	}
	public Date getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}
	
	
}
