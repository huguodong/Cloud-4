package com.ssitcloud.devmgmt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>2016年4月27日 上午11:18:46
 * @author hjc
 */
public class MonitorTempParam {

	/**
	 * device_monitor_template
	 *  device_mon_tpl_idx	int(11) NOT NULL
		device_mon_tpl_id	varchar(10) NOT NULL显示模板ID
		device_mon_tpl_desc	varchar(500) NOT NULL显示模板描述
		device_mon_type_idx	int(11) NOT NULL设备类型
	 */
	/** device_monitor_template自增idx */
	private String device_mon_tpl_idx;
	/** 显示模板id */
	private String device_mon_tpl_id;
	/** 显示模板描述 */
	private String device_mon_tpl_desc;
	/** 设备类型idx */
	private String device_mon_type_idx;
	
	
	private List<MonitortTempDetailParam> monitorDetailList = new ArrayList<>();
	
	
	public String getDevice_mon_tpl_idx() {
		return device_mon_tpl_idx;
	}
	public void setDevice_mon_tpl_idx(String device_mon_tpl_idx) {
		this.device_mon_tpl_idx = device_mon_tpl_idx;
	}
	public String getDevice_mon_tpl_id() {
		return device_mon_tpl_id;
	}
	public void setDevice_mon_tpl_id(String device_mon_tpl_id) {
		this.device_mon_tpl_id = device_mon_tpl_id;
	}
	public String getDevice_mon_tpl_desc() {
		return device_mon_tpl_desc;
	}
	public void setDevice_mon_tpl_desc(String device_mon_tpl_desc) {
		this.device_mon_tpl_desc = device_mon_tpl_desc;
	}
	public String getDevice_mon_type_idx() {
		return device_mon_type_idx;
	}
	public void setDevice_mon_type_idx(String device_mon_type_idx) {
		this.device_mon_type_idx = device_mon_type_idx;
	}
	public List<MonitortTempDetailParam> getMonitorDetailList() {
		return monitorDetailList;
	}
	public void setMonitorDetailList(List<MonitortTempDetailParam> monitorDetailList) {
		this.monitorDetailList = monitorDetailList;
	}
	
	
}
