package com.ssitcloud.system.entity;




public class DeviceRunConfigRemoteEntity{

	
	/**
	 * 
	device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
	device_run_id	int(11) NOT NULL模板ID或设备ID
	library_idx		int(11) NOT NULL馆ID
	run_config_idx	int(11) NOT NULL设备端参数配置初始数据
	run_conf_value	mediumtext NOT NULL基础配置参数
	updatetime		timestamp NOT NULL
	 */
	private String run_conf_type;
	private String  run_conf_value;
	
	public static final Integer DEVICE_TPL_TYPE_IS_MODEL=0;
	public static final Integer DEVICE_TPL_TYPE_IS_DATA=1;

	public DeviceRunConfigRemoteEntity(){}
	public DeviceRunConfigRemoteEntity(Integer id){
		
	}

	public String getRun_conf_value() {
		return run_conf_value;
	}
	public void setRun_conf_value(String run_conf_value) {
		this.run_conf_value = run_conf_value;
	}
	public String getRun_conf_type() {
		return run_conf_type;
	}
	public void setRun_conf_type(String run_conf_type) {
		this.run_conf_type = run_conf_type;
	}

	
	
}
