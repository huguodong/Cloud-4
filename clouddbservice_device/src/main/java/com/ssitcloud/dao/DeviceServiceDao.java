package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.DeviceServicePageEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceBaseEntity;

public interface DeviceServiceDao {
	
	public DeviceServiceEntity insertDeviceService(DeviceServiceEntity serviceEntity);
	
	public DeviceServicePageEntity queryDeviceServiceByPage(DeviceServicePageEntity pageEntity);
	
	public List<DeviceServiceEntity> queryDeviceServiceByParams(Map<String, String> params); 
	
	public int updateDeviceServiceOperateTime(DeviceServiceEntity deviceServiceEntity); 
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return Map<library_idx, service_id>
	 */
	public Map<Integer, String> queryServiceIdbyLibIdx();
	public Map<String, String> queryServiceIdbyLibId();
	/**
	 * 根据lServiceId获取特殊设备（3D导航、人流量、智能家具）信息
	 * @return Map<library_idx, service_id>
	 */
	public List<DeviceServiceBaseEntity> queryDeviceServiceByServiceId(Map<String, String> params);
	
	public int editDeviceService(DeviceServiceEntity serviceEntity);
	
	public int deleteDeviceService(Map<String, String> map);
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取服务名称 分组serviceid
	 * @return Map<library_idx, service_id>
	 */
	public Map<Integer, String> queryServiceIdbyServiceIdx();
	
	public int selectCountDeviceService(Map<String, String> params);

}
