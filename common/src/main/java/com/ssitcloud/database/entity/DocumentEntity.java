package com.ssitcloud.database.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DocumentEntity implements Serializable {

	private String id = null;
	private List<String> fields = new ArrayList<>();
	private String jsonStr = null;

	public DocumentEntity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}
	
	public void addField(String field){
		this.fields.add(field);
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

}