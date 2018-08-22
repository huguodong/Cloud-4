package com.ssitcloud.dblib.entity;

public class DownloadCfgSyncEntity {
	private String device_id;
	private String library_id;//实际上library_idx
	private String lib_id;//真正的library_id
	private String queue_id;//真正的queue_id
	private String service_id;//真正的service_id  
	private String dBName;
	private String table;
	private String keyName;
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
	public String getdBName() {
		return dBName;
	}
	public void setdBName(String dBName) {
		this.dBName = dBName;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	
}
