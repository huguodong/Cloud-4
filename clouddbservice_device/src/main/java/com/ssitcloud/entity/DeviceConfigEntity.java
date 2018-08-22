package com.ssitcloud.entity;

import org.mybatis.generator.internal.util.EqualsUtil;

public class DeviceConfigEntity{
	/**
	 * 
		FieldTypeComment
		device_idx Integer(11) NOT NULL设备ID
		library_idx Integer(11) NOT NULL馆ID
		device_ext_tpl_idx Integer(11) NOT NULL硬件配置模板ID
		device_ext_tpl_flg Integer(11) NOT NULL是否采用模板 1是 0否
		device_monitor_tpl_idx Integer(11) NOT NULL显示配置模板ID
		device_monitor_tpl_flg Integer(11) NOT NULL显示是否采用模板 1是 0否
		device_run_tpl_idx Integer(11) NOT NULL设备运行模板ID
		device_run_tpl_flg Integer(11) NOT NULL运行是否采用模板 1是 0否
		device_dbsync_tpl_idx Integer(11) NOT NULL数据同步模板ID
		device_dbsync_tpl_flg
	 */
	private Integer device_idx;
	private Integer library_idx;
	private Integer device_ext_tpl_idx;
	private Integer device_ext_tpl_flg;
	private Integer device_monitor_tpl_flg;
	private Integer device_monitor_tpl_idx;
	private Integer device_run_tpl_idx;
	private Integer device_run_tpl_flg;
	private Integer device_dbsync_tpl_idx;
	private Integer device_dbsync_tpl_flg;
	
	public static final Integer IS_MODEL=1;
	public static final Integer IS_NOT_MODEL=0;

	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getDevice_ext_tpl_idx() {
		return device_ext_tpl_idx;
	}
	public void setDevice_ext_tpl_idx(Integer device_ext_tpl_idx) {
		this.device_ext_tpl_idx = device_ext_tpl_idx;
	}
	public Integer getDevice_ext_tpl_flg() {
		return device_ext_tpl_flg;
	}
	public void setDevice_ext_tpl_flg(Integer device_ext_tpl_flg) {
		this.device_ext_tpl_flg = device_ext_tpl_flg;
	}
	public Integer getDevice_monitor_tpl_flg() {
		return device_monitor_tpl_flg;
	}
	public void setDevice_monitor_tpl_flg(Integer device_monitor_tpl_flg) {
		this.device_monitor_tpl_flg = device_monitor_tpl_flg;
	}
	public Integer getDevice_run_tpl_idx() {
		return device_run_tpl_idx;
	}
	public void setDevice_run_tpl_idx(Integer device_run_tpl_idx) {
		this.device_run_tpl_idx = device_run_tpl_idx;
	}
	public Integer getDevice_run_tpl_flg() {
		return device_run_tpl_flg;
	}
	public void setDevice_run_tpl_flg(Integer device_run_tpl_flg) {
		this.device_run_tpl_flg = device_run_tpl_flg;
	}
	public Integer getDevice_dbsync_tpl_idx() {
		return device_dbsync_tpl_idx;
	}
	public void setDevice_dbsync_tpl_idx(Integer device_dbsync_tpl_idx) {
		this.device_dbsync_tpl_idx = device_dbsync_tpl_idx;
	}
	public Integer getDevice_dbsync_tpl_flg() {
		return device_dbsync_tpl_flg;
	}
	public void setDevice_dbsync_tpl_flg(Integer device_dbsync_tpl_flg) {
		this.device_dbsync_tpl_flg = device_dbsync_tpl_flg;
	}
	public Integer getDevice_monitor_tpl_idx() {
		return device_monitor_tpl_idx;
	}
	public void setDevice_monitor_tpl_idx(Integer device_monitor_tpl_idx) {
		this.device_monitor_tpl_idx = device_monitor_tpl_idx;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==null) return this==null;
		if(this==obj) return true;
		if(obj instanceof DeviceConfigEntity){
			DeviceConfigEntity deviceConfig=(DeviceConfigEntity)obj;
			//不判断device_idx 和 library_idx
			if(!EqualsUtil.areEqual(this.getDevice_dbsync_tpl_flg(),deviceConfig.getDevice_dbsync_tpl_flg())
				||!EqualsUtil.areEqual(this.getDevice_dbsync_tpl_idx(),deviceConfig.getDevice_dbsync_tpl_idx())
				||!EqualsUtil.areEqual(this.getDevice_ext_tpl_flg(),deviceConfig.getDevice_ext_tpl_flg())
				||!EqualsUtil.areEqual(this.getDevice_ext_tpl_idx(),deviceConfig.getDevice_ext_tpl_idx())
				||!EqualsUtil.areEqual(this.getDevice_monitor_tpl_flg(),deviceConfig.getDevice_monitor_tpl_flg())
				||!EqualsUtil.areEqual(this.getDevice_monitor_tpl_idx(),deviceConfig.getDevice_monitor_tpl_idx())
				||!EqualsUtil.areEqual(this.getDevice_run_tpl_flg(),deviceConfig.getDevice_run_tpl_flg())
				||!EqualsUtil.areEqual(this.getDevice_run_tpl_idx(),deviceConfig.getDevice_run_tpl_idx())
				){
				//有一个不相等return false
				return false;
			}else{
				return true;
			}
		}
		return false;
	}
	/**
	 * 检查实体是否存在需要更新的不为NULL值的字段
		 Integer device_ext_tpl_idx;
		 Integer device_ext_tpl_flg;
		 Integer device_monitor_tpl_flg;
		 Integer device_monitor_tpl_idx;
		 Integer device_run_tpl_idx;
		 Integer device_run_tpl_flg;
		 Integer device_dbsync_tpl_idx;
		 Integer device_dbsync_tpl_flg;
	 * @return
	 */
	public boolean hasUpdateField(){
		if(device_ext_tpl_idx!=null){
			return true;
		}
		if(device_ext_tpl_flg!=null){
			return true;
		}
		if(device_monitor_tpl_flg!=null){
			return true;
		}
		if(device_monitor_tpl_idx!=null){
			return true;
		}
		if(device_run_tpl_idx!=null){
			return true;
		}
		if(device_run_tpl_flg!=null){
			return true;
		}
		if(device_dbsync_tpl_idx!=null){
			return true;
		}
		if(device_dbsync_tpl_flg!=null){
			return true;
		}
		return false;
	}
	
	
}
