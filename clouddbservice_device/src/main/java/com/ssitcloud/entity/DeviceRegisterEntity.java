package com.ssitcloud.entity;



public class DeviceRegisterEntity {
		//device 
		private DeviceEntity device;
		//device_dbsync_template
		private DeviceDBSyncTempEntity device_dbsync_template;
		//device_ext_template
		private DeviceExtTempEntity device_ext_template;
		//device_run_template
		private DeviceRunTempEntity device_run_template;
		//device_monitor_template
		private DeviceMonTempEntity device_monitor_template;
		
		public DeviceEntity getDevice() {
			return device;
		}
		public void setDevice(DeviceEntity device) {
			this.device = device;
		}
		public DeviceDBSyncTempEntity getDevice_dbsync_template() {
			return device_dbsync_template;
		}
		public void setDevice_dbsync_template(
				DeviceDBSyncTempEntity device_dbsync_template) {
			this.device_dbsync_template = device_dbsync_template;
		}
		public DeviceExtTempEntity getDevice_ext_template() {
			return device_ext_template;
		}
		public void setDevice_ext_template(DeviceExtTempEntity device_ext_template) {
			this.device_ext_template = device_ext_template;
		}
		public DeviceRunTempEntity getDevice_run_template() {
			return device_run_template;
		}
		public void setDevice_run_template(DeviceRunTempEntity device_run_template) {
			this.device_run_template = device_run_template;
		}
		public DeviceMonTempEntity getDevice_monitor_template() {
			return device_monitor_template;
		}
		public void setDevice_monitor_template(
				DeviceMonTempEntity device_monitor_template) {
			this.device_monitor_template = device_monitor_template;
		}
		
}
