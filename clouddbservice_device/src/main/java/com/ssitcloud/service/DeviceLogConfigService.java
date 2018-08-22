package com.ssitcloud.service;

import java.util.List;

import com.ssitcloud.entity.DeviceLogConfigEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月11日
 */
public interface DeviceLogConfigService {

	/**
	 * 添加设备运行日志配置
	 * 
	 * @param deviceLogConfigEntity
	 * @return
	 * @author hwl
	 */
	public abstract int addDeviceLogConfig(
			DeviceLogConfigEntity deviceLogConfigEntity);

	/**
	 * 更新设备运行日志配置
	 * 
	 * @param deviceLogConfigEntity
	 * @return
	 * @author hwl
	 */
	public abstract int updateDeviceLogConfig(
			DeviceLogConfigEntity deviceLogConfigEntity);

	/**
	 * 删除设备运行日志配置
	 * 
	 * @param id
	 * @return
	 * @author hwl
	 */
	public abstract int deleteDeviceLogConfig(
			DeviceLogConfigEntity deviceLogConfigEntity);

	/**
	 * 条件查询设备运行日志配置
	 * 
	 * @param deviceLogConfigEntity
	 * @return
	 * @author hwl
	 */
	public abstract List<DeviceLogConfigEntity> selectDeviceLogConfig(
			DeviceLogConfigEntity deviceLogConfigEntity);
}
