package com.ssitcloud.business.common.util;

import java.util.List;
import java.util.Map;

public class ReturnResultEntity {
	
	//private String library_id;
	//private String device_id;
	private String table;
	private Map<String,String> fields;
	private List<Map<String,Object>> records;
	

	public List<Map<String, Object>> getRecords() {
		return records;
	}
	public void setRecords(List<Map<String, Object>> records) {
		this.records = records;
	}
	public Map<String,String> getFields() {
		return fields;
	}
	public void setFields(Map<String,String> fields) {
		this.fields = fields;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	
	
}
