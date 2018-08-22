package com.ssitcloud.devmgmt.entity;

import java.io.Serializable;

public class FileUploadState implements Serializable{

	private static final long serialVersionUID = 1L;

	private String tableName;
	private String fileName;
	//1表示正在上传,2表示上传完成
	private String state;
	private String filePath;
	private String tableField;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getTableField() {
		return tableField;
	}
	public void setTableField(String tableField) {
		this.tableField = tableField;
	}
}
