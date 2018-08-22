package com.ssitcloud.devmgmt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>2016年4月26日 下午8:09:44
 * @author hjc
 */
public class RunTempParam {

	
	/** 自增IDX */
	private String device_tpl_idx; 
	/** 模板ID */
	private String device_tpl_ID; 
	/** 模板描述 */
	private String device_tpl_desc; 
	/** 设备类型IDX */
	private String device_type;
	
	List<RunTempDetailParam> runDetailList = new ArrayList<>();
	
	public String getDevice_tpl_idx() {
		return device_tpl_idx;
	}
	public void setDevice_tpl_idx(String device_tpl_idx) {
		this.device_tpl_idx = device_tpl_idx;
	}
	public String getDevice_tpl_ID() {
		return device_tpl_ID;
	}
	public void setDevice_tpl_ID(String device_tpl_ID) {
		this.device_tpl_ID = device_tpl_ID;
	}
	public String getDevice_tpl_desc() {
		return device_tpl_desc;
	}
	public void setDevice_tpl_desc(String device_tpl_desc) {
		this.device_tpl_desc = device_tpl_desc;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public List<RunTempDetailParam> getRunDetailList() {
		return runDetailList;
	}
	public void setRunDetailList(List<RunTempDetailParam> runDetailList) {
		this.runDetailList = runDetailList;
	}
	
	
	
}
