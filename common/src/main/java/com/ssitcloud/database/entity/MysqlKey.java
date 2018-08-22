package com.ssitcloud.database.entity;

//主键外键
public class MysqlKey implements java.io.Serializable {

	private String key_name = null;
	private String field = null;
	private String ref_database = null;
	private String ref_table = null;
	private String ref_field = null;
	private String update_rule = null;
	private String delete_rule = null;

	public MysqlKey() {
	}

	public String getKey_name() {
		return key_name;
	}

	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getRef_database() {
		return ref_database;
	}

	public void setRef_database(String ref_database) {
		this.ref_database = ref_database;
	}

	public String getRef_table() {
		return ref_table;
	}

	public void setRef_table(String ref_table) {
		this.ref_table = ref_table;
	}

	public String getRef_field() {
		return ref_field;
	}

	public void setRef_field(String ref_field) {
		this.ref_field = ref_field;
	}

	public String getUpdate_rule() {
		return update_rule;
	}

	public void setUpdate_rule(String update_rule) {
		this.update_rule = update_rule;
	}

	public String getDelete_rule() {
		return delete_rule;
	}

	public void setDelete_rule(String delete_rule) {
		this.delete_rule = delete_rule;
	}
	
}