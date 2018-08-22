package com.ssitcloud.entity;

/**
 * 统计函数的主函数实体
 * author huanghuang
 * 2017年3月21日 下午4:48:46
 */
public class FunMaindataEntity {
	private Integer main_idx;//int(11) NOT NULL 主函数IDX
	private String main_desc;//varchar(50) NOT NULL 函数说明
	private String main_mark;//varchar(100) NULL 函数备注
	
	public Integer getMain_idx() {
		return main_idx;
	}
	public void setMain_idx(Integer main_idx) {
		this.main_idx = main_idx;
	}
	public String getMain_desc() {
		return main_desc;
	}
	public void setMain_desc(String main_desc) {
		this.main_desc = main_desc;
	}
	public String getMain_mark() {
		return main_mark;
	}
	public void setMain_mark(String main_mark) {
		this.main_mark = main_mark;
	}
	
	
}
