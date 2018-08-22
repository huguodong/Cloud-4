package com.ssitcloud.dbstatistics.entity;

/**
 * 静态主类型实体
 * author huanghuang
 * 2017年3月20日 下午1:38:38
 */
public class StatisticsMaintypeEntity {
	private Integer maintype_idx;//int(11) NOT NULL 主类型ID
	private Integer type_code;//int(11) NOT NULL 类型属性区分 1固态 2随馆变化数据
	private String type_value;//varchar(50) NULL 类型说明
	private String type_desc;//varchar(200) NULL 类型备注
	public Integer getMaintype_idx() {
		return maintype_idx;
	}
	public void setMaintype_idx(Integer maintype_idx) {
		this.maintype_idx = maintype_idx;
	}
	public Integer getType_code() {
		return type_code;
	}
	public void setType_code(Integer type_code) {
		this.type_code = type_code;
	}
	public String getType_value() {
		return type_value;
	}
	public void setType_value(String type_value) {
		this.type_value = type_value;
	}
	public String getType_desc() {
		return type_desc;
	}
	public void setType_desc(String type_desc) {
		this.type_desc = type_desc;
	}
	
}
