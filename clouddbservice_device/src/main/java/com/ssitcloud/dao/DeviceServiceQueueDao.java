package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.DeviceServiceQueue;

public interface DeviceServiceQueueDao {
	
	public DeviceServiceQueue insertDeviceServiceQueue(DeviceServiceQueue queue);
	public List<DeviceServiceQueue> queryDeviceServiceQueue(Map<String, String> map);
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取消息队列 分组queueid
	 * @return Map<service_idx, queue_id>
	 */
	public Map<Integer, String> queryQueueIdbyServiceIdx();
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx及获取消息队列 queueid获取消息队列配置信息
	 * @param map
	 * @return
	 */
	public List<DeviceServiceQueue>	queryQueueInfobyServiceIdxAndQueueId(Map<String, String> map);
	public int editDeviceServiceQueue(DeviceServiceQueue deviceServiceQueue);
	public int deleteDeviceServiceQueue(Map<String, String> params);

}
