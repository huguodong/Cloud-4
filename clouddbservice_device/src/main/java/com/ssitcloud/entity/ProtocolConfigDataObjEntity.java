package com.ssitcloud.entity;

import java.util.List;

public class ProtocolConfigDataObjEntity {
	
	private Integer protocol_tpl_idx;//主键
	
	private Integer library_idx;//图书馆主键
	
	private Integer protocol_type;//1 SIP2 2 NCIP
	
	private String protocol_tpl_desc;//模板名
	
	private List<SelfCheckProtocolEntity> protocol_config_arr;//详细配置信息
	
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getProtocol_type() {
		return protocol_type;
	}
	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}
	public List<SelfCheckProtocolEntity> getProtocol_config_arr() {
		return protocol_config_arr;
	}
	public void setProtocol_config_arr(
			List<SelfCheckProtocolEntity> protocol_config_arr) {
		this.protocol_config_arr = protocol_config_arr;
	}
	public Integer getProtocol_tpl_idx() {
		return protocol_tpl_idx;
	}
	public void setProtocol_tpl_idx(Integer protocol_tpl_idx) {
		this.protocol_tpl_idx = protocol_tpl_idx;
	}
	public String getProtocol_tpl_desc() {
		return protocol_tpl_desc;
	}
	public void setProtocol_tpl_desc(String protocol_tpl_desc) {
		this.protocol_tpl_desc = protocol_tpl_desc;
	}
	
	
	
}
