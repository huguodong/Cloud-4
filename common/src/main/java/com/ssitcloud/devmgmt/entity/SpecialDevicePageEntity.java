package com.ssitcloud.devmgmt.entity;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class SpecialDevicePageEntity extends DatagridPageEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer idx;
	private Integer library_idx;
	/**设备类型*/
	private String device_type_id;
	/**设备名称**/
	private String device_name;
	/**区域*/
	private String zone;
	/**通道*/
	private String passage;
	/**设备路径*/
	private String extpath;
	/**是否需要切换采集数据*/
	private int needshift;
	/**连接串*/
	private String constring;
	
	private String device_id;
	
	private String device_desc;
	
	private String library_id;
	
	private String service_id;
	
	private int service_idx;
	
	private String device_type_desc;
	
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	
	public String getDevice_type_id() {
		return device_type_id;
	}
	public void setDevice_type_id(String device_type_id) {
		this.device_type_id = device_type_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getPassage() {
		return passage;
	}
	public void setPassage(String passage) {
		this.passage = passage;
	}
	public String getExtpath() {
		return extpath;
	}
	public void setExtpath(String extpath) {
		this.extpath = extpath;
	}
	public int getNeedshift() {
		return needshift;
	}
	public void setNeedshift(int needshift) {
		this.needshift = needshift;
	}
	public String getConstring() {
		return constring;
	}
	public void setConstring(String constring) {
		this.constring = constring;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_desc() {
		return device_desc;
	}
	public void setDevice_desc(String device_desc) {
		this.device_desc = device_desc;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public int getService_idx() {
		return service_idx;
	}
	public void setService_idx(int service_idx) {
		this.service_idx = service_idx;
	}
	public String getDevice_type_desc() {
		return device_type_desc;
	}
	public void setDevice_type_desc(String device_type_desc) {
		this.device_type_desc = device_type_desc;
	}
	
	
	
}
