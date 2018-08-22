package com.ssitcloud.shelfmgmt.entity;

public class ShelfConfigEntity {

	private int shelf_config_idx;
	private String shelf_config_id;
	private String shelf_config_name;
	private String shelf_ip;
	private Long shelf_port;
	private Integer version_stamp;
	public int getShelf_config_idx() {
		return shelf_config_idx;
	}
	public void setShelf_config_idx(int shelf_config_idx) {
		this.shelf_config_idx = shelf_config_idx;
	}
	public String getShelf_config_id() {
		return shelf_config_id;
	}
	public void setShelf_config_id(String shelf_config_id) {
		this.shelf_config_id = shelf_config_id;
	}
	public String getShelf_config_name() {
		return shelf_config_name;
	}
	public void setShelf_config_name(String shelf_config_name) {
		this.shelf_config_name = shelf_config_name;
	}
	public String getShelf_ip() {
		return shelf_ip;
	}
	public void setShelf_ip(String shelf_ip) {
		this.shelf_ip = shelf_ip;
	}
	public Long getShelf_port() {
		return shelf_port;
	}
	public void setShelf_port(Long shelf_port) {
		this.shelf_port = shelf_port;
	}
	public Integer getVersion_stamp() {
		return version_stamp;
	}
	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}
}
