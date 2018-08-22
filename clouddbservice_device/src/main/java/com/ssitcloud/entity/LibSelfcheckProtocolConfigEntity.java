package com.ssitcloud.entity;



/**
 * 
 * table library_selfcheck_protocol_config
	FieldTypeComment
	protocol_field_idx	int(11) NOT NULLID
	protocol_field_name	varchar(50) NOT NULL指令描述
	protocol_field_desc	varchar(200) NOT NULL指令名
	protocol_field_mark	varchar(1000) NULL备注
 *  @author lbh
 *
 */
public class LibSelfcheckProtocolConfigEntity {
	private Integer protocol_field_idx;
	//指令英文名
	private String protocol_field_name;
	//指令中文名
	private String protocol_field_desc;
	//备注
	private String protocol_field_mark;
	//类型
	private Integer protocol_type;
	//指令代码
	private String  protocol_show;
	
	public Integer getProtocol_field_idx() {
		return protocol_field_idx;
	}
	public void setProtocol_field_idx(Integer protocol_field_idx) {
		this.protocol_field_idx = protocol_field_idx;
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
	public String getProtocol_field_mark() {
		return protocol_field_mark;
	}
	public void setProtocol_field_mark(String protocol_field_mark) {
		this.protocol_field_mark = protocol_field_mark;
	}
	public Integer getProtocol_type() {
		return protocol_type;
	}
	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}
	public String getProtocol_show() {
		return protocol_show;
	}
	public void setProtocol_show(String protocol_show) {
		this.protocol_show = protocol_show;
	}
	
	
	
}
