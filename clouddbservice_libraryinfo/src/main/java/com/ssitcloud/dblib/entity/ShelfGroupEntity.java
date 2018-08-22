package com.ssitcloud.dblib.entity;

import java.util.Date;

public class ShelfGroupEntity {

	private String lib_id;
	private String shelf_group_id;
	private String shelf_group_name;
	private String shelf_group_desc;
	private Integer version_stamp;
	private String shelf_config_id;
	private Date updatetime;
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getShelf_group_id() {
		return shelf_group_id;
	}
	public void setShelf_group_id(String shelf_group_id) {
		this.shelf_group_id = shelf_group_id;
	}
	public String getShelf_group_name() {
		return shelf_group_name;
	}
	public void setShelf_group_name(String shelf_group_name) {
		this.shelf_group_name = shelf_group_name;
	}
	public String getShelf_group_desc() {
		return shelf_group_desc;
	}
	public void setShelf_group_desc(String shelf_group_desc) {
		this.shelf_group_desc = shelf_group_desc;
	}
	
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getShelf_config_id() {
		return shelf_config_id;
	}
	public void setShelf_config_id(String shelf_config_id) {
		this.shelf_config_id = shelf_config_id;
	}
}
