package com.ssitcloud.entity;

import java.sql.Timestamp;

/**
 * 定时任务触发器实体
 * author huanghuang
 * 2017年2月28日 上午11:45:15
 */
public class TimedTaskTriggerEntity {
	private Integer tri_idx;//int(11) NOT NULL 自增ID
	private String table_name;//varchar(50) NOT NULL 表名
	private Integer data_idx;//int(11) NOT NULL 原始表记录号
	private String change_state;//int(11) NULL 图书馆idx
	private Timestamp create_time;//timestamp NOT NULL 创建时间
	
	public Integer getTri_idx() {
		return tri_idx;
	}
	public void setTri_idx(Integer tri_idx) {
		this.tri_idx = tri_idx;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public Integer getData_idx() {
		return data_idx;
	}
	public void setData_idx(Integer data_idx) {
		this.data_idx = data_idx;
	}
	public String getChange_state() {
		return change_state;
	}
	public void setChange_state(String change_state) {
		this.change_state = change_state;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
	
	
	
	
	
}
