package com.ssitcloud.devmgmt.entity;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class DeviceServiceTypePageEntity extends DatagridPageEntity{
	
	private int service_type_idx;
	private String service_type_id;
	private String service_type_name;
	private String service_type_db;
	public int getService_type_idx() {
		return service_type_idx;
	}
	public void setService_type_idx(int service_type_idx) {
		this.service_type_idx = service_type_idx;
	}
	public String getService_type_id() {
		return service_type_id;
	}
	public void setService_type_id(String service_type_id) {
		this.service_type_id = service_type_id;
	}
	public String getService_type_name() {
		return service_type_name;
	}
	public void setService_type_name(String service_type_name) {
		this.service_type_name = service_type_name;
	}
	public String getService_type_db() {
		return service_type_db;
	}
	public void setService_type_db(String service_type_db) {
		this.service_type_db = service_type_db;
	}
	
	
}
