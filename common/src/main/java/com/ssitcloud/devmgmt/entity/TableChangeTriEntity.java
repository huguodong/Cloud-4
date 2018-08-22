package com.ssitcloud.devmgmt.entity;

import java.sql.Timestamp;

/**
 * 表table_change_tri
 * ：该表有触发器插入数据
 * 
	FieldType		Comment
	tri_idx			int(11) NOT NULLID号
	tabel_name		varchar(50) NOT NULL表名
	data_idx		int(11) NOT NULL数据变经的主键或设备ID
	data_type		varchar(5) NULL数据类型
	change_state	varchar(10) NOT NULLI新增 U 更新 D删除
	createtime		timestamp NOT NULL时间


 * @package: com.ssitcloud.entity
 * @classFile: TableChangeTriEntity
 * @author: liuBh
 * @description: TODO
 */
public class TableChangeTriEntity {
	private Integer tri_idx;
	private String table_name;
	private Integer data_idx;
	private String data_type;
	private String change_state;
	private Timestamp createtime;
	private Timestamp requestTime;
	
	public TableChangeTriEntity(){}
	public TableChangeTriEntity(Integer data_idx){
		this.data_idx=data_idx;
	}
	
	public Integer getTri_idx() {
		return tri_idx;
	}
	public void setTri_idx(Integer tri_idx) {
		this.tri_idx = tri_idx;
	}

	public Integer getData_idx() {
		return data_idx;
	}
	public void setData_idx(Integer data_idx) {
		this.data_idx = data_idx;
	}
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getChange_state() {
		return change_state;
	}
	public void setChange_state(String change_state) {
		this.change_state = change_state;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Timestamp requestTime) {
		this.requestTime = requestTime;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	

}
