package com.ssitcloud.devmgmt.entity;

import java.util.Date;

public class SyncConfigEntity {
	private String db_name;
	private String table_name;
	private Integer lib_idx;
	private String lib_id;
	private String device_id;
	private Integer issync;
	private String sync_cycle;
	private String sync_field_list;
	private String sync_type;
	private String sync_state;
	private Date last_sync_time;
	private Date last_modify_time;
	
	public String getDb_name() {
		return db_name;
	}
	public void setDb_name(String db_name) {
		this.db_name = db_name;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public Integer getIssync() {
		return issync;
	}
	public void setIssync(Integer issync) {
		this.issync = issync;
	}
	public String getSync_cycle() {
		return sync_cycle;
	}
	public void setSync_cycle(String sync_cycle) {
		this.sync_cycle = sync_cycle;
	}
	public String getSync_field_list() {
		return sync_field_list;
	}
	public void setSync_field_list(String sync_field_list) {
		this.sync_field_list = sync_field_list;
	}
	public String getSync_type() {
		return sync_type;
	}
	public void setSync_type(String sync_type) {
		this.sync_type = sync_type;
	}
	public String getSync_state() {
		return sync_state;
	}
	public void setSync_state(String sync_state) {
		this.sync_state = sync_state;
	}
	public Date getLast_sync_time() {
		return last_sync_time;
	}
	public void setLast_sync_time(Date last_sync_time) {
		this.last_sync_time = last_sync_time;
	}
	public Date getLast_modify_time() {
		return last_modify_time;
	}
	public void setLast_modify_time(Date last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
}
