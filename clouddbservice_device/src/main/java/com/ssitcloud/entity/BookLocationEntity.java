package com.ssitcloud.entity;

/**
 * BookLocationEntity图书馆藏地实体
 * author huanghuang
 * 2017年3月6日 下午6:27:56
 */
public class BookLocationEntity {
	private Integer location_idx;//int(11) NOT NULL 记录号
	private Integer lib_idx;//int(11) NOT NULL 图书馆idx
	private String location_code;//varchar(10) NOT NULL 馆藏地代码
	private String location_name;//varchar(200) NOT NULL 馆藏地名称
	private String location_mark;//varchar(200) NULL 馆藏地备注
	
	public Integer getLocation_idx() {
		return location_idx;
	}
	public void setLocation_idx(Integer location_idx) {
		this.location_idx = location_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getLocation_code() {
		return location_code;
	}
	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}
	public String getLocation_name() {
		return location_name;
	}
	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}
	public String getLocation_mark() {
		return location_mark;
	}
	public void setLocation_mark(String location_mark) {
		this.location_mark = location_mark;
	}
	
}
