package com.ssitcloud.entity;

public class ImportConfigEntity {
	private Integer import_tpl_idx;//	模板ID
	private Integer import_tpl_type;//		"模板类型  1教工导入模板 2学生导入模板"
	private String lib_id;			//图书馆id
	private String lib_name;
	private Integer library_idx;
	private String import_tpl_value;//		模板配置值

	public Integer getImport_tpl_idx() {
		return import_tpl_idx;
	}

	public void setImport_tpl_idx(Integer import_tpl_idx) {
		this.import_tpl_idx = import_tpl_idx;
	}

	public Integer getImport_tpl_type() {
		return import_tpl_type;
	}

	public void setImport_tpl_type(Integer import_tpl_type) {
		this.import_tpl_type = import_tpl_type;
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

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}

	public String getImport_tpl_value() {
		return import_tpl_value;
	}

	public void setImport_tpl_value(String import_tpl_value) {
		this.import_tpl_value = import_tpl_value;
	}
}
