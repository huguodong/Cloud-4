package com.ssitcloud.dbstatistics.entity;

public class StatisticsReltypeEntity {
	private Integer maintype_idx;//对应maintype表的idx
	private String data_base;//数据库名
	private String data_tables;//表名
	private String data_fields;//字段名
	private String lib_fields;//对应的图书馆idx
	private String data_mark;//备注
	public Integer getMaintype_idx() {
		return maintype_idx;
	}
	public void setMaintype_idx(Integer maintype_idx) {
		this.maintype_idx = maintype_idx;
	}
	public String getData_base() {
		return data_base;
	}
	public void setData_base(String data_base) {
		this.data_base = data_base;
	}
	public String getData_tables() {
		return data_tables;
	}
	public void setData_tables(String data_tables) {
		this.data_tables = data_tables;
	}
	public String getData_fields() {
		return data_fields;
	}
	public void setData_fields(String data_fields) {
		this.data_fields = data_fields;
	}
	public String getData_mark() {
		return data_mark;
	}
	public void setData_mark(String data_mark) {
		this.data_mark = data_mark;
	}
	public String getLib_fields() {
		return lib_fields;
	}
	public void setLib_fields(String lib_fields) {
		this.lib_fields = lib_fields;
	}
	
	

}
