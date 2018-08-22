package com.ssitcloud.entity;

public class ConfigFieldEntity {
	private Integer config_idx;//导入配置模板ID
	private String config_table;//导入模板名（功能名）
	private String config_field;//	导入模板导出字段字段
	private String config_field_desc;	//字段说明
	private String ref_statistics;//统计或分组关联数据
	public Integer getConfig_idx() {
		return config_idx;
	}
	public void setConfig_idx(Integer config_idx) {
		this.config_idx = config_idx;
	}
	public String getConfig_table() {
		return config_table;
	}
	public void setConfig_table(String config_table) {
		this.config_table = config_table;
	}
	public String getConfig_field() {
		return config_field;
	}
	public void setConfig_field(String config_field) {
		this.config_field = config_field;
	}
	public String getConfig_field_desc() {
		return config_field_desc;
	}
	public void setConfig_field_desc(String config_field_desc) {
		this.config_field_desc = config_field_desc;
	}
	public String getRef_statistics() {
		return ref_statistics;
	}
	public void setRef_statistics(String ref_statistics) {
		this.ref_statistics = ref_statistics;
	}
}
