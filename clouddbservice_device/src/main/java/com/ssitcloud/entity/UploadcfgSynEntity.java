package com.ssitcloud.entity;

import java.util.List;
import java.util.Map;


/**
 * 
req= 
	 {
	 	"device_id":"",
	 	"library_id":"",
	 	"table":"",
	 	"fields":""
	    "records": [
	        {
	            "run_conf_type": "asc_config",
	            "run_conf_value": "{\"IP\":\"127.0.0.1\",\"PORT\":\"8080\"}",
	            "updatetime": "xxxx-xx-xx"
	        },
	        {
	            "run_conf_type": "center_config",
	            "run_conf_value": "{\"IP\":\"127.0.0.1\",\"PORT\":\"8080\"}",
	            "updatetime": "xxx-xx-xx"
	        }
	    ]
	}
 * @package: com.ssitcloud.datasync.entity
 * @classFile: UploadcfgSynEntity
 * @author: liuBh
 * @description: TODO
 */
public class UploadcfgSynEntity {
	private String device_id;
	
	private String library_id;
	
	private String table;
	
	private Map<String,Object> fields;
	
	private List<Map<String,Object>> records;
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Map<String, Object> getFields() {
		return fields;
	}
	public void setFields(Map<String, Object> fields) {
		this.fields = fields;
	}
	public List<Map<String,Object>> getRecords() {
		return records;
	}
	public void setRecords(List<Map<String,Object>> records) {
		this.records = records;
	}



}
