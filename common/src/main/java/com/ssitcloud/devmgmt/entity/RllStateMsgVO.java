package com.ssitcloud.devmgmt.entity;

import java.util.Map;

/**
 * 
 * @comment 人流量统计vo对应消息队列中的信息
 * 
 * @author yeyalin
 * @data 2017年10月10日
 */
public class RllStateMsgVO {

	/* 图书馆id */
	private String library_id;
	/* 设备类型id */
	private String device_id;
	/**
	 * 采集设备统计信息统计时间
	 */
	private Map<String, Object> content;
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public Map<String, Object> getContent() {
		return content;
	}
	public void setContent(Map<String, Object> content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "RllStateMsgVO [library_id=" + library_id + ", device_id="
				+ device_id + ", content=" + content + "]";
	}

	
}
