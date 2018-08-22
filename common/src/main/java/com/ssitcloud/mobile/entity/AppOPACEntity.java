package com.ssitcloud.mobile.entity;

import java.util.Map;

/**
 * OPAC实体
 * @author LXP
 * @version 创建时间：2017年4月5日 下午3:34:43
 */
public class AppOPACEntity {
	private Integer type;//类型，1设备类型的opac 2自助图书馆类型的opac
	private String devName;//设备名
	private String location;//设备地址
	private Map<String, Object> idData;//标识数据，用于标识opac的数据，查询opac内数目信息时应回传此数据
	private Map<String, Object> otherData;//opac其他数据
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDevName() {
		return devName;
	}
	public void setDevName(String devName) {
		this.devName = devName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Map<String, Object> getIdData() {
		return idData;
	}
	public void setIdData(Map<String, Object> idData) {
		this.idData = idData;
	}
	public Map<String, Object> getOtherData() {
		return otherData;
	}
	public void setOtherData(Map<String, Object> otherData) {
		this.otherData = otherData;
	}
}
