package com.ssitcloud.shelfmgmt.entity;

public class ShelfGroupEntity {

	private int shelf_group_idx;
	private String lib_id;
	private String shelf_group_id;
	private int shelf_config_idx;
	private String shelf_group_name;
	private String shelf_group_desc;
	private Integer version_stamp;
	public int getShelf_group_idx() {
		return shelf_group_idx;
	}
	public void setShelf_group_idx(int shelf_group_idx) {
		this.shelf_group_idx = shelf_group_idx;
	}
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
	public int getShelf_config_idx() {
		return shelf_config_idx;
	}
	public void setShelf_config_idx(int shelf_config_idx) {
		this.shelf_config_idx = shelf_config_idx;
	}
	@Override
	public String toString() {
		return "ShelfGroupEntity [shelf_group_idx=" + shelf_group_idx
				+ ", lib_id=" + lib_id + ", shelf_group_id=" + shelf_group_id
				+ ", shelf_config_idx=" + shelf_config_idx
				+ ", shelf_group_name=" + shelf_group_name
				+ ", shelf_group_desc=" + shelf_group_desc + ", version_stamp="
				+ version_stamp + "]";
	}
	
}
