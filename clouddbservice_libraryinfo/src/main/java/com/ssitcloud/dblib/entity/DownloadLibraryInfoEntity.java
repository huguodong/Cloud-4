package com.ssitcloud.dblib.entity;


public class DownloadLibraryInfoEntity {
	
	private String device_id;//设备ID
	
	private String library_id;//图书馆id
	
	private String device_idx;//设备idx
	
	private String library_idx;//图书馆idx
	
	private String table;//表名
	
	private Integer page;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getLibrary_id() {
		return library_id;
	}

	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}

	public String getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(String device_idx) {
		this.device_idx = device_idx;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
