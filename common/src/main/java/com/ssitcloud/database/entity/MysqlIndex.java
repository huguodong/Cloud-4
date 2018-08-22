package com.ssitcloud.database.entity;

import java.util.*;
//索引
public class MysqlIndex implements java.io.Serializable {

	private String keyName = null;
	private String type = null;
	private int cardinality = 0;
	private List<String> fields = new ArrayList<String>();
	private String columnStr = null;

	public MysqlIndex() {
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setCardinality(int cardinality) {
		this.cardinality = cardinality;
	}

	public int getCardinality() {
		return this.cardinality;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public String getField(int index) {
		return (String) this.fields.get(index);
	}

	public void addField(String field) {
		this.fields.add(field);
	}

	public void removeField(String field) {
		this.fields.remove(field);
	}

	public String getColumnStr() {
		return columnStr;
	}

	public void setColumnStr(String columnStr) {
		this.columnStr = columnStr;
	}
	
}