package com.ssitcloud.datasync.entity;


/**
 * req={
			"device_id":"",
			"library_id":"",
			"table":""
		}
 * @package: com.ssitcloud.datasync.entity
 * @classFile: DownloadOsbSynEntity
 * @description: TODO
 */
public class DownloadOsbSynEntity {
	private String device_id;
	private String library_id;
	private String table;
	private String path;
	private String fileType;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
