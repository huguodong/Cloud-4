package com.ssitcloud.node.entity;

import java.util.Date;

public class FileManagerEntity {
	
	public static String NCIPTEMPLATE = "down_NcipTemplate";
	public static String ACSPROTOCOLS = "down_acs_protocols"; 
	
    private Integer file_idx;//上传文件id,自增
    private String device_id;//设备id
    private String device_idx;//设备idx
    private String library_id;//图书馆id
    private String library_idx;//图书馆idx
    private String file_name;//文件名
    private String file_localPath; //文件本地路径
    private String file_path;//文件服务器路径
    private String file_version;//文件版本
    private Date create_time;//创建时间
    private Long file_size;//上传文件大小
    private String operation;//操作
    private String table;//下发表
    public Long getFile_size() {
        return file_size;
    }

    public void setFile_size(Long file_size) {
        this.file_size = file_size;
    }

    public Integer getFile_idx() {
        return file_idx;
    }

    public void setFile_idx(Integer file_idx) {
        this.file_idx = file_idx;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getDevice_idx() {
        return device_idx;
    }

    public void setDevice_idx(String device_idx) {
        this.device_idx = device_idx;
    }

    public String getLibrary_id() {
        return library_id;
    }

    public void setLibrary_id(String library_id) {
        this.library_id = library_id;
    }

    public String getLibrary_idx() {
        return library_idx;
    }

    public void setLibrary_idx(String library_idx) {
        this.library_idx = library_idx;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_localPath() {
        return file_localPath;
    }

    public void setFile_localPath(String file_localPath) {
        this.file_localPath = file_localPath;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_version() {
        return file_version;
    }

    public void setFile_version(String file_version) {
        this.file_version = file_version;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Override
    public String toString() {
        return "FileManagerEntity{" +
                "file_idx=" + file_idx +
                ", device_id='" + device_id + '\'' +
                ", device_idx='" + device_idx + '\'' +
                ", library_id='" + library_id + '\'' +
                ", library_idx='" + library_idx + '\'' +
                ", file_name='" + file_name + '\'' +
                ", file_localPath='" + file_localPath + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_version='" + file_version + '\'' +
                ", create_time=" + create_time +
                '}';
    }
}
