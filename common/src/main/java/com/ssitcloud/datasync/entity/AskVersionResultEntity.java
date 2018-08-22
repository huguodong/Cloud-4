package com.ssitcloud.datasync.entity;

/**
 * 字段名称	类型	说明
	device_id		String	设备ID<br/>
	library_id		String	馆ID<br/>
	upgradeStrategy	String	升级策略<br/>
		version		String	要升级的版本号   target_version<br/>
		path		String 	升级包下载路径 <br/>  
格式：<br/>
 * 格式
 * {
 * "device_id":"",
 * "library_id":"",
 * "upgradeStrategy":[
 * 	{"version":"","path":""},{"version":"","path":""},{...},{}
 * 
 * ]
 * 
 * }
 * 

 * @package: com.ssitcloud.datasync.entity
 * @classFile: AskVersionResultEntity
 * @author: liuBh
 * @description: TODO
 */
public class AskVersionResultEntity {
	private String device_id;
	private String library_id;
	private String[] upgradeStrategy;//"[{"version":"","path":""}]"

	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String[] getUpgradeStrategy() {
		return upgradeStrategy;
	}
	public void setUpgradeStrategy(String[] upgradeStrategy) {
		this.upgradeStrategy = upgradeStrategy;
	}

	
}
