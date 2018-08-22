package com.ssitcloud.app_reader.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * OPAC实体
 * @author LXP
 * @version 创建时间：2017年4月5日 下午3:34:43
 */
public class AppOPACEntity implements Serializable{
	private Integer type;//类型，1设备类型的opac 2自助图书馆类型的opac
	private String devName;//设备名
	private String location;//设备地址
	private Map<String, Object> idData;//标识数据，用于标识opac的数据，查询opac内书目信息时应回传此数据
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

	@Override
	public String toString() {
		return "AppOPACEntity{" +
				"type=" + type +
				", devName='" + devName + '\'' +
				", location='" + location + '\'' +
				", idData=" + idData +
				", otherData=" + otherData +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AppOPACEntity that = (AppOPACEntity) o;

		if (type != null ? !type.equals(that.type) : that.type != null) return false;
		if (devName != null ? !devName.equals(that.devName) : that.devName != null) return false;
		if (location != null ? !location.equals(that.location) : that.location != null)
			return false;
		if (idData != null ? !idData.equals(that.idData) : that.idData != null) return false;
		return otherData != null ? otherData.equals(that.otherData) : that.otherData == null;

	}

	@Override
	public int hashCode() {
		int result = type != null ? type.hashCode() : 0;
		result = 31 * result + (devName != null ? devName.hashCode() : 0);
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (idData != null ? idData.hashCode() : 0);
		result = 31 * result + (otherData != null ? otherData.hashCode() : 0);
		return result;
	}
}
