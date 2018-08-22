package com.ssitcloud.datasync.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 * @package: com.ssitcloud.datasync.entity
 * @classFile: UpgradeStrategyEntity
 * @author: liuBh
 * @description: TODO
 */
public class UpgradeStrategyEntity {
	private String device_id;
	
	private String library_id;
	
	private String target_version;
	
	private String old_version;
	
	private List<String[]> upgrade_paths;
	
	public String getTarget_version() {
		return target_version;
	}
	public void setTarget_version(String target_version) {
		this.target_version = target_version;
	}
	public List<String[]> getUpgrade_paths() {
		return upgrade_paths;
	}
	public void setUpgrade_paths(List<String[]> upgrade_paths) {
		this.upgrade_paths = upgrade_paths;
	}
	
	public Map<String,List<VersionUrlEntity>> transforToVersionUrl(){
		Map<String,List<VersionUrlEntity>> versionGroupByOldvesion=new HashMap<>();
		if(upgrade_paths!=null){
			for(String[] versions:upgrade_paths){
				String oldVersion=versions[0];
				List<VersionUrlEntity> VersionUrlEntitys=new ArrayList<>();
				for(String version:versions){
					VersionUrlEntity versionUrl=new VersionUrlEntity();
					versionUrl.setVersion(version);
					VersionUrlEntitys.add(versionUrl);
					
				}
				versionGroupByOldvesion.put(oldVersion, VersionUrlEntitys);
				
			}
		}
		return versionGroupByOldvesion;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getOld_version() {
		return old_version;
	}
	public void setOld_version(String old_version) {
		this.old_version = old_version;
	}
	public String getLibrary_id() {
		return library_id;
	}
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}

}
