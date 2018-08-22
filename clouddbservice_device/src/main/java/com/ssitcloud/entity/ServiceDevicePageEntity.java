package com.ssitcloud.entity;

import java.util.List;

import org.codehaus.jackson.map.annotate.JsonRootName;

import com.ssitcloud.common.entity.DatagridPageEntity;


@JsonRootName(value="servicedevice")
public class ServiceDevicePageEntity extends DatagridPageEntity {

	
	private static final long serialVersionUID = 1L;
	/* 服务设备所属图书馆的idx */
	private Integer library_idx;
	/* 服务设备idx */
	private Integer device_idx;
	/* 服务设备的服务类型id */
	private Integer device_model_idx;
	/* 服务设备的名称 */
	private String service_name;
	/* 服务设备名 */
	private String device_names;
	/* 服务设备id*/
	private String device_ids;
	/* 服务设备id */
	private String library_id;
	/* 服务设备名 */
	private Integer service_idx;
	/* 服务设备特征码 */
	private String service_id;
	/* 服务设备类型id */
	private String service_type_id;
	
	/**
	 * 图书馆 约束
	 */
	private List<Integer> lib_idx_list;
	
	
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}
	public Integer getDevice_model_idx() {
		return device_model_idx;
	}
	public void setDevice_model_idx(Integer device_model_idx) {
		this.device_model_idx = device_model_idx;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getDevice_names() {
		return device_names;
	}
	public void setDevice_names(String device_names) {
		this.device_names = device_names;
	}
	public String getDevice_ids() {
		return device_ids;
	}
	public void setDevice_ids(String device_ids) {
		this.device_ids = device_ids;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public Integer getService_idx() {
		return service_idx;
	}
	public void setService_idx(Integer service_idx) {
		this.service_idx = service_idx;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getService_type_id() {
		return service_type_id;
	}
	public void setService_type_id(String service_type_id) {
		this.service_type_id = service_type_id;
	}
	public List<Integer> getLib_idx_list() {
		return lib_idx_list;
	}
	public void setLib_idx_list(List<Integer> lib_idx_list) {
		this.lib_idx_list = lib_idx_list;
	}
}
