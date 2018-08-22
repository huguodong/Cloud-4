package com.ssitcloud.entity;

import java.util.List;

public class DeviceMonitorTempAndConfigEntity {

	private DeviceMonTempEntity device_mon_tpl;
	
	private List<DeviceMonConfigEntity> dev_mon_conf_Arr;

	public DeviceMonTempEntity getDevice_mon_tpl() {
		return device_mon_tpl;
	}

	public void setDevice_mon_tpl(DeviceMonTempEntity device_mon_tpl) {
		this.device_mon_tpl = device_mon_tpl;
	}

	public List<DeviceMonConfigEntity> getDev_mon_conf_Arr() {
		return dev_mon_conf_Arr;
	}

	public void setDev_mon_conf_Arr(List<DeviceMonConfigEntity> dev_mon_conf_Arr) {
		this.dev_mon_conf_Arr = dev_mon_conf_Arr;
	}
}
