package com.ssitcloud.dblib.entity;

import java.util.List;
import java.util.Map;


/**
 * 
 *
 * <p>2017年4月11日 下午3:52:58  
 * @author hjc 
 *
 */
public class UploadLibraryInfoEntity {
	private String device_id;//设备ID
	
	private String library_id;//图书馆id
	
	private String device_idx;//设备idx
	
	private String library_idx;//图书馆idx
	
	private String nowLibrary_idx;//当前图书馆idx
	
	private String nowLibrary_id;//当前图书馆id
	
	private String device_type;//设备类型，目前只有两种类型的处理方式， 一个是ssl 自助图书馆， 另一个是sta-c 点检车
	
	private String table;
	
	private Map<String,Object> fields;
	
	private List<Map<String,Object>> records;
	
	
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(String device_idx) {
		this.device_idx = device_idx;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public String getNowLibrary_idx() {
		return nowLibrary_idx;
	}
	public void setNowLibrary_idx(String nowLibrary_idx) {
		this.nowLibrary_idx = nowLibrary_idx;
	}
	public String getNowLibrary_id() {
		return nowLibrary_id;
	}
	public void setNowLibrary_id(String nowLibrary_id) {
		this.nowLibrary_id = nowLibrary_id;
	}
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
