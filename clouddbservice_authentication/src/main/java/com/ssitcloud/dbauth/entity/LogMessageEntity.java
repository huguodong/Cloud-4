package com.ssitcloud.dbauth.entity;

/**
 * 
	log_idx	int(11) NOT NULLID
	log_code	varchar(6) NOT NULL消息代码
	log_desc	varchar(50) NOT NULL消息说明
	log_mark	varchar(200) NULL数据参数说明
 * @author lbh
 *
 */
public class LogMessageEntity {
	
	private Integer log_idx;
	private String log_code;
	private String log_desc;
	private String log_mark;
	
	public Integer getLog_idx() {
		return log_idx;
	}
	public String getLog_code() {
		return log_code;
	}
	public void setLog_code(String log_code) {
		this.log_code = log_code;
	}
	public String getLog_desc() {
		return log_desc;
	}
	public void setLog_desc(String log_desc) {
		this.log_desc = log_desc;
	}
	public String getLog_mark() {
		return log_mark;
	}
	public void setLog_mark(String log_mark) {
		this.log_mark = log_mark;
	}
	public void setLog_idx(Integer log_idx) {
		this.log_idx = log_idx;
	}
	
}
