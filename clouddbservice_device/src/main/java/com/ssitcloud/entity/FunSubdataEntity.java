package com.ssitcloud.entity;

/**
 * 统计函数的子函数实体
 * author huanghuang
 * 2017年3月21日 下午4:48:46
 */
public class FunSubdataEntity {
	
	private Integer sub_idx;//int(11) NOT NULL 子函数IDX
	private Integer main_idx;//int(11) NOT NULL 主函数IDX
	private String sub_value;//varchar(20) NULL 函数操作符
	private String sub_desc;//varchar(100) NULL 函数描述
	public Integer getSub_idx() {
		return sub_idx;
	}
	public void setSub_idx(Integer sub_idx) {
		this.sub_idx = sub_idx;
	}
	public Integer getMain_idx() {
		return main_idx;
	}
	public void setMain_idx(Integer main_idx) {
		this.main_idx = main_idx;
	}
	public String getSub_value() {
		return sub_value;
	}
	public void setSub_value(String sub_value) {
		this.sub_value = sub_value;
	}
	public String getSub_desc() {
		return sub_desc;
	}
	public void setSub_desc(String sub_desc) {
		this.sub_desc = sub_desc;
	}
	
	
}
