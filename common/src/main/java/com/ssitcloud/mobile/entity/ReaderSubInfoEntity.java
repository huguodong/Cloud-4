package com.ssitcloud.mobile.entity;

/**
 * 读者关联基本信息实体
 * author huanghuang
 * 2017年2月9日 下午4:00:03
 */
public class ReaderSubInfoEntity {
	private Integer reader_idx;//int(11) NOT NULL 读者记录号
	private Integer infotype_idx;//int(11) NOT NULL 字段类型代码
	private String infotype_value;//varchar(200) NULL 字段值
	
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public Integer getInfotype_idx() {
		return infotype_idx;
	}
	public void setInfotype_idx(Integer infotype_idx) {
		this.infotype_idx = infotype_idx;
	}
	public String getInfotype_value() {
		return infotype_value;
	}
	public void setInfotype_value(String infotype_value) {
		this.infotype_value = infotype_value;
	}
	
	
	
}
