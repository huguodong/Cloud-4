package com.ssitcloud.entity;

/**
 * BookCirtypeEntity图书流通类型实体
 * author huanghuang
 * 2017年3月6日 下午6:33:33
 */
public class BookCirtypeEntity {
	private Integer cirtype_idx;//int(11) NOT NULL 记录号
	private Integer lib_idx;//int(11) NOT NULL 图书馆idx
	private String cirtype_code;//varchar(10) NOT NULL 图书流通类型代码
	private String cirtype_name;//varchar(200) NOT NULL 图书流通类型名
	private String cirtype_mark;//varchar(200) NULL 图书流通类型备注
	
	public Integer getCirtype_idx() {
		return cirtype_idx;
	}
	public void setCirtype_idx(Integer cirtype_idx) {
		this.cirtype_idx = cirtype_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getCirtype_code() {
		return cirtype_code;
	}
	public void setCirtype_code(String cirtype_code) {
		this.cirtype_code = cirtype_code;
	}
	public String getCirtype_name() {
		return cirtype_name;
	}
	public void setCirtype_name(String cirtype_name) {
		this.cirtype_name = cirtype_name;
	}
	public String getCirtype_mark() {
		return cirtype_mark;
	}
	public void setCirtype_mark(String cirtype_mark) {
		this.cirtype_mark = cirtype_mark;
	}
	
	
}
