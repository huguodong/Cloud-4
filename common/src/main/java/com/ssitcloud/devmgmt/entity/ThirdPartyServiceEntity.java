package com.ssitcloud.devmgmt.entity;

import java.util.Date;

/**
 * 第三方应用服务
 * 
 * @author xiebf
 * 
 */
public class ThirdPartyServiceEntity {

	/** 主键id */
	private Integer service_idx;

	/** 服务appkey */
	private String app_key;

	/*** 服务名称 */
	private String service_name;

	/** 服务描述 */
	private String service_desc;

	/** 图书馆主键idx */
	private Integer library_idx;

	/** 请求ip,用作白名单验证 */
	private String request_ip;

	/** 限制访问次数 */
	private Integer access_times;

	/** 是否启用 */
	private Integer isDisable;

	/** 操作时间 */
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
