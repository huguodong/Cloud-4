package com.ssitcloud.dbauth.entity;

/**
 * LibraryInfo表 和 MetadataInfotype表数据
 * @comment 
 * @date 2016年5月25日
 * @author hwl
 */
public class LibraryMetatypeInfoEntity {

	/** 图书馆自增ID */
	private Integer library_idx;
	/** 源数据表ID */
	private Integer infotype_idx;
	/** 字段值 */
	private String info_value;
	/** 信息类型 */
	private String info_type;
	/** 信息描述 */
	private String info_type_desc;
	
	
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
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}
	public String getInfo_type_desc() {
		return info_type_desc;
	}
	public void setInfo_type_desc(String info_type_desc) {
		this.info_type_desc = info_type_desc;
	}
	
	
}
