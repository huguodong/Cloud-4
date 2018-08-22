package com.ssitcloud.entity;

/**
 * BookMediatypeEntity图书载体类型实体
 * author huanghuang
 * 2017年3月6日 下午6:41:29
 */
public class BookMediatypeEntity {
	private Integer media_idx;//int(11) NOT NULL 记录号
	private Integer lib_idx;//int(11) NOT NULL 图书馆idx
	private String media_code;//varchar(20) NOT NULL 载体类型代码
	private String media_name;//varchar(200) NOT NULL 载体名称
	private String media_mark;//varchar(200) NULL 备注
	
	public Integer getMedia_idx() {
		return media_idx;
	}
	public void setMedia_idx(Integer media_idx) {
		this.media_idx = media_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getMedia_code() {
		return media_code;
	}
	public void setMedia_code(String media_code) {
		this.media_code = media_code;
	}
	public String getMedia_name() {
		return media_name;
	}
	public void setMedia_name(String media_name) {
		this.media_name = media_name;
	}
	public String getMedia_mark() {
		return media_mark;
	}
	public void setMedia_mark(String media_mark) {
		this.media_mark = media_mark;
	}
	
	
}
