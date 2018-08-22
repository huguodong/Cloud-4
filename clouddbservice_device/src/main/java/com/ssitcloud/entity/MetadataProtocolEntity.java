package com.ssitcloud.entity;
/**
 * 
 *	protocol_field_idx	int(11) NOT NULL      ID
 *	protocol_show		varchar(50) NOT NULL  指令代码
 *	protocol_field_name	varchar(50) NOT NULL  指令名
 *	protocol_field_desc	varchar(200) NOT NULL 指令说明
 *	protocol_field_sort	int(3) NULL 	                  排序
 *
 */
public class MetadataProtocolEntity {
	
	private Integer protocol_field_idx;
	
	private String protocol_show;
	
	private String protocol_field_name;
	
	private String protocol_field_desc;
	
	private Integer protocol_field_sort;
	
	public Integer getProtocol_field_idx() {
		return protocol_field_idx;
	}
	public void setProtocol_field_idx(Integer protocol_field_idx) {
		this.protocol_field_idx = protocol_field_idx;
	}
	public String getProtocol_show() {
		return protocol_show;
	}
	public void setProtocol_show(String protocol_show) {
		this.protocol_show = protocol_show;
	}
	public String getProtocol_field_name() {
		return protocol_field_name;
	}
	public void setProtocol_field_name(String protocol_field_name) {
		this.protocol_field_name = protocol_field_name;
	}
	public String getProtocol_field_desc() {
		return protocol_field_desc;
	}
	public void setProtocol_field_desc(String protocol_field_desc) {
		this.protocol_field_desc = protocol_field_desc;
	}
	public Integer getProtocol_field_sort() {
		return protocol_field_sort;
	}
	public void setProtocol_field_sort(Integer protocol_field_sort) {
		this.protocol_field_sort = protocol_field_sort;
	}
	
}
