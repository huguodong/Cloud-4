package com.ssitcloud.devregister.entity;

import com.ssitcloud.devmgmt.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.devmgmt.entity.DeviceExtTempEntity;
import com.ssitcloud.devmgmt.entity.DeviceMonTempEntity;
import com.ssitcloud.devmgmt.entity.DeviceRunTempEntity;


/**
 * 注册信息，包括<br/>
 * device表<br/>
 * device_dbsync_template表<br/>
 * device_ext_template表<br/>
 * device_run_template表<br/>
 * device_monitor_template表<br/>
 * 5个表的信息
 * 
 * @package: com.ssitcloud.devregister.entity
 * @classFile: RegisterEntity
 * @author: liuBh
 * @description: TODO
 */
public class RegisterEntity {
	//device  table
	private DeviceEntity device;
	//device_dbsync_template table
	private DeviceDBSyncTempEntity dbSyncTemp;
	//device_ext_template table
	private DeviceExtTempEntity extTemp;
	//device_run_template table
	private DeviceRunTempEntity runTemp;
	//device_monitor_template table
	private DeviceMonTempEntity monitorTemp;
	
	public DeviceEntity getDevice() {
		return device;
	}
	public void setDevice(DeviceEntity device) {
		this.device = device;
	}
	public DeviceDBSyncTempEntity getDbSyncTemp() {
		return dbSyncTemp;
	}
	public void setDbSyncTemp(DeviceDBSyncTempEntity dbSyncTemp) {
		this.dbSyncTemp = dbSyncTemp;
	}
	public DeviceExtTempEntity getExtTemp() {
		return extTemp;
	}
	public void setExtTemp(DeviceExtTempEntity extTemp) {
		this.extTemp = extTemp;
	}
	public DeviceRunTempEntity getRunTemp() {
		return runTemp;
	}
	public void setRunTemp(DeviceRunTempEntity runTemp) {
		this.runTemp = runTemp;
	}
	public DeviceMonTempEntity getMonitorTemp() {
		return monitorTemp;
	}
	public void setMonitorTemp(DeviceMonTempEntity monitorTemp) {
		this.monitorTemp = monitorTemp;
	}
	
}
