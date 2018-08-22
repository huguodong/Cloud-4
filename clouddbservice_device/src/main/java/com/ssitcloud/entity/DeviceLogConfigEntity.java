package com.ssitcloud.entity;

/**
 * 
 * @comment 设备运行日志配置表 
 * 			`device_log_idx` int(11) NOT NULL AUTO_INCREMENT,
 *          `device_idx` int(11) NOT NULL COMMENT '设备ID', 
 *          `runlog_level` int(11) NOT NULL COMMENT '日志级别，0-不记录，1-error，2-info，3-warning，4-debug',
 *          `runlog_type` int(11) NOT NULL COMMENT '保存方式，0-db，1-file',
 *          `runlog_filesize` int(11) NOT NULL COMMENT '日志保存文件的大小',
 *          `library_idx` int(11) NOT NULL,
 * @author hwl
 * @data 2016年4月11日
 */
public class DeviceLogConfigEntity {

	private int device_log_idx;
	private int device_idx ;
	private int runlog_level;
	private int runlog_type;
	private int runlog_filesize;
	private int library_idx;
	public int getDevice_log_idx() {
		return device_log_idx;
	}
	public void setDevice_log_idx(int device_log_idx) {
		this.device_log_idx = device_log_idx;
	}
	public int getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(int device_idx) {
		this.device_idx = device_idx;
	}
	public int getRunlog_level() {
		return runlog_level;
	}
	public void setRunlog_level(int runlog_level) {
		this.runlog_level = runlog_level;
	}
	public int getRunlog_type() {
		return runlog_type;
	}
	public void setRunlog_type(int runlog_type) {
		this.runlog_type = runlog_type;
	}
	public int getRunlog_filesize() {
		return runlog_filesize;
	}
	public void setRunlog_filesize(int runlog_filesize) {
		this.runlog_filesize = runlog_filesize;
	}
	public int getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(int library_idx) {
		this.library_idx = library_idx;
	}
	
	
}
