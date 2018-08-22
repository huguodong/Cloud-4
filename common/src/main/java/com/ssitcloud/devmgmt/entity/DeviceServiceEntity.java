package com.ssitcloud.devmgmt.entity;

import java.util.List;

public class DeviceServiceEntity  extends DeviceServiceBaseEntity{
	
	
	/**设备类型*/
	private String service_type_name;
	
	/**区域*/
	private String region;
	
	private List<DeviceServiceQueue> queueList;
	
	private String service_type_db;

	public String getService_type_name() {
		return service_type_name;
	}

	public void setService_type_name(String service_type_name) {
		this.service_type_name = service_type_name;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<DeviceServiceQueue> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<DeviceServiceQueue> queueList) {
		this.queueList = queueList;
	}

	public String getService_type_db() {
		return service_type_db;
	}

	public void setService_type_db(String service_type_db) {
		this.service_type_db = service_type_db;
	}
	
	

}
