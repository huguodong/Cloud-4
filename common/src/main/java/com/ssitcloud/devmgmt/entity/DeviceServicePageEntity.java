package com.ssitcloud.devmgmt.entity;

import java.util.Date;
import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class DeviceServicePageEntity extends DatagridPageEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**主键id*/
	private int service_idx;
	
	/**服务id*/
	private String service_id;
	
	/**设备类型*/
	private int device_model_idx;
	
	/**图书馆主键id*/
	private String library_idx;
	
	/**图书馆id*/
	private String library_id;
	
	/***服务名称*/
	private String service_name;
	
	/**区域*/
	private String region;
	
	/**服务描述*/
	private String service_desc;
	
	/**设备类型*/
	private String service_type_name;
	
	private String request_ip;
	
	private Date operate_time;
	private List<DeviceServiceQueue> queueList;
	

	public int getService_idx() {
		return service_idx;
	}

	public void setService_idx(int service_idx) {
		this.service_idx = service_idx;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}


	public int getDevice_model_idx() {
		return device_model_idx;
	}

	public void setDevice_model_idx(int device_model_idx) {
		this.device_model_idx = device_model_idx;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
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


	public String getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}

	public List<DeviceServiceQueue> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<DeviceServiceQueue> queueList) {
		this.queueList = queueList;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getService_type_name() {
		return service_type_name;
	}

	public void setService_type_name(String service_type_name) {
		this.service_type_name = service_type_name;
	}

	public String getRequest_ip() {
		return request_ip;
	}

	public void setRequest_ip(String request_ip) {
		this.request_ip = request_ip;
	}

	public Date getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}

	
	
	

}
