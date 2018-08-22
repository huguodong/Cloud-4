package com.ssitcloud.authentication.entity;

/** 
 * 元数据表
 * <p>2016年3月29日 下午5:29:13  
 * @author hjc 
 *
 */
public class MetadataInfotypeEntity {
	/** 自增ID */
	private Integer infotype_idx;
	/** 信息类型 */
	private String info_type;
	/** 信息描述 */
	private String info_type_desc;
	
	public Integer getInfotype_idx() {
		return infotype_idx;
	}
	public void setInfotype_idx(Integer infotype_idx) {
		this.infotype_idx = infotype_idx;
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
