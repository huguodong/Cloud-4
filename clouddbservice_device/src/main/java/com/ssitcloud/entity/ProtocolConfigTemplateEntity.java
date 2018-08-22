package com.ssitcloud.entity;

import java.sql.Timestamp;
import java.util.List;

import com.ssitcloud.common.entity.DatagridPageEntity;
/**
 * ACS 模板
 * @author Administrator
 *
 */
public class ProtocolConfigTemplateEntity extends DatagridPageEntity<ProtocolConfigTemplateEntity>{
	
	private static final long serialVersionUID = 1L;

	private Integer protocol_tpl_idx;
	
	private Integer library_idx;
	
	private Integer protocol_type;
	
	private String protocol_tpl_desc;
	
	private String protocol_field_names;

	private Timestamp updatetime;
	
	private List<Integer> library_idx_arr; 
	
	private Integer version_stamp;
	
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

	public String getProtocol_field_names() {
		return protocol_field_names;
	}

	public void setProtocol_field_names(String protocol_field_names) {
		this.protocol_field_names = protocol_field_names;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	public List<Integer> getLibrary_idx_arr() {
		return library_idx_arr;
	}

	public void setLibrary_idx_arr(List<Integer> library_idx_arr) {
		this.library_idx_arr = library_idx_arr;
	}

	public Integer getVersion_stamp() {
		return version_stamp;
	}

	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}

}
