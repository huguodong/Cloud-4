package com.ssitcloud.dblib.entity;

/**
 * 读者信息原始字段实体
 * author huanghuang
 * 2017年2月9日 下午4:02:34
 */
public class MetaDataInfoTypeEntity {
	private Integer infotype_idx;//int(11) NOT NULL 原始字段记录号
	private String info_type_desc;//varchar(100) NULL字段描述
	public Integer getInfotype_idx() {
		return infotype_idx;
	}
	public void setInfotype_idx(Integer infotype_idx) {
		this.infotype_idx = infotype_idx;
	}
	public String getInfo_type_desc() {
		return info_type_desc;
	}
	public void setInfo_type_desc(String info_type_desc) {
		this.info_type_desc = info_type_desc;
	}
	
	
	
	
}
