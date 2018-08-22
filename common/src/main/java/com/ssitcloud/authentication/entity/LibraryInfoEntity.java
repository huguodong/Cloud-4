package com.ssitcloud.authentication.entity;

/**
 * 图书馆信息描述表
 * <p>2016年4月5日 上午11:07:10
 * @author hjc
 *
 */
public class LibraryInfoEntity {
	/** 图书馆自增ID */
	private Integer library_idx;
	/** 源数据表ID */
	private Integer infotype_idx;
	/** 字段值 */
	private String info_value;
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getInfotype_idx() {
		return infotype_idx;
	}
	public void setInfotype_idx(Integer infotype_idx) {
		this.infotype_idx = infotype_idx;
	}
	public String getInfo_value() {
		return info_value;
	}
	public void setInfo_value(String info_value) {
		this.info_value = info_value;
	}
	
	
}
