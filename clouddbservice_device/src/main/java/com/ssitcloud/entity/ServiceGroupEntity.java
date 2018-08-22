package com.ssitcloud.entity;

/**
 * @comment 业务组表。表名：service_group
 * @author hwl
 * 
 * @data 2016年4月5日
 * 
 */
public class ServiceGroupEntity {

	/* 业务组自增长ID */
	private Integer service_group_idx;
	/* 业务组ID */
	private String service_group_id;
	/* 馆id */
	private Integer library_idx;
	/* 业务组名称 */
	private String service_group_name;
	/* 业务组描述 */
	private String service_group_desc;
	
	private Integer version_stamp;

	public ServiceGroupEntity(){
		
	}
	public ServiceGroupEntity(String service_group_id){
		this.service_group_id= service_group_id;
	}
	
	public Integer getService_group_idx() {
		return service_group_idx;
	}

	public void setService_group_idx(Integer service_group_idx) {
		this.service_group_idx = service_group_idx;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getService_group_name() {
		return service_group_name;
	}

	public void setService_group_name(String service_group_name) {
		this.service_group_name = service_group_name;
	}

	public String getService_group_desc() {
		return service_group_desc;
	}

	public void setService_group_desc(String service_group_desc) {
		this.service_group_desc = service_group_desc;
	}

	public String getService_group_id() {
		return service_group_id;
	}

	public void setService_group_id(String service_group_id) {
		this.service_group_id = service_group_id;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}

}
