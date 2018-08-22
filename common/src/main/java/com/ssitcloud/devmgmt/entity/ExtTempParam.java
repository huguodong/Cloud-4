package com.ssitcloud.devmgmt.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>2016年4月26日 下午7:20:38
 * @author hjc
 */
public class ExtTempParam {
	
	/** 模板自增idx */
	public String device_tpl_idx;
	/** 模板ID */
	public String device_tpl_ID;
	/** 模板描述 */
	public String device_tpl_desc;
	/** 设备类型IDX */
	public String device_type;
	
	/** 外设模板的详细信息 */
	public List<ExtTempDetailParam> extDetailList = new ArrayList<>();

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

	public List<ExtTempDetailParam> getExtDetailList() {
		return extDetailList;
	}

	public void setExtDetailList(List<ExtTempDetailParam> extDetailList) {
		this.extDetailList = extDetailList;
	}

	
	
	

}
