package com.ssitcloud.dblib.entity;

/**
 * 读者默认信息实体
 * author huanghuang
 * 2017年2月9日 下午3:58:38
 */
public class ReaderDefaultEntity {
	private Integer default_idx;//int(11) NOT NULL 原始字段记录号
	private Integer reader_idx;//int(11) NOT NULL 字段描述
	private String default_device;//varchar(50) NULL 默认查询的设备，device_idx的集合，逗号分隔
	
	public Integer getDefault_idx() {
		return default_idx;
	}
	public void setDefault_idx(Integer default_idx) {
		this.default_idx = default_idx;
	}
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public String getDefault_device() {
		return default_device;
	}
	public void setDefault_device(String default_device) {
		this.default_device = default_device;
	}
	
	
}
