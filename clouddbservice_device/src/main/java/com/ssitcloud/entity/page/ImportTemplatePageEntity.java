package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class ImportTemplatePageEntity extends DatagridPageEntity {
	private static final long serialVersionUID = 1L;
	private Integer import_tpl_idx;//模板IDX
	private String import_tpl_id;//模板ID
	private String import_tpl_desc;//模板名
	private Integer import_tpl_type;//"模板类型 "
	private String lib_id;
	private String lib_name;
	private String import_tpl_value;//模板配置值
	
	public Integer getImport_tpl_idx() {
		return import_tpl_idx;
	}
	public void setImport_tpl_idx(Integer import_tpl_idx) {
		this.import_tpl_idx = import_tpl_idx;
	}
	public String getImport_tpl_id() {
		return import_tpl_id;
	}
	public void setImport_tpl_id(String import_tpl_id) {
		this.import_tpl_id = import_tpl_id;
	}
	public String getImport_tpl_desc() {
		return import_tpl_desc;
	}
	public void setImport_tpl_desc(String import_tpl_desc) {
		this.import_tpl_desc = import_tpl_desc;
	}
	public Integer getImport_tpl_type() {
		return import_tpl_type;
	}
	public void setImport_tpl_type(Integer import_tpl_type) {
		this.import_tpl_type = import_tpl_type;
	}
	public String getImport_tpl_value() {
		return import_tpl_value;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getLib_name() {
		return lib_name;
	}
	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}
	public void setImport_tpl_value(String import_tpl_value) {
		this.import_tpl_value = import_tpl_value;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
