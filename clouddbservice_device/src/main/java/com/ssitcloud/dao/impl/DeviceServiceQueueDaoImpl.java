package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceServiceQueueDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceQueue;

@Repository
public class DeviceServiceQueueDaoImpl extends CommonDaoImpl implements DeviceServiceQueueDao{

	/***添加队列*/
	public DeviceServiceQueue insertDeviceServiceQueue(DeviceServiceQueue queue) {
		getSqlSession().insert("deviceServiceQueue.insertDeviceServiceQueue", queue);
		return queue;
	}

	/**查询队列*/
	public List<DeviceServiceQueue> queryDeviceServiceQueue(Map<String, String> map) {
		return getSqlSession().selectList("deviceServiceQueue.queryDeviceServiceQueue", map);
	}
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取消息队列 分组queueid
	 * @return Map<service_idx, queue_id>
	 */
	@Override
	public Map<Integer, String> queryQueueIdbyServiceIdx(){
		return getSqlSession().selectMap("deviceServiceQueue.queryQueueIdbyServiceIdx", "device_service_idx");
	}
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx及获取消息队列 queueid获取消息队列配置信息
	 * @param map
	 * @return
	 */
	@Override
	public List<DeviceServiceQueue>	queryQueueInfobyServiceIdxAndQueueId(Map<String, String> map){
		return getSqlSession().selectList("deviceServiceQueue.queryQueueInfobyServiceIdxAndQueueId", map);
	}
	
	@Override
	public int editDeviceServiceQueue(DeviceServiceQueue deviceServiceQueue) {
		return getSqlSession().update("deviceServiceQueue.editDeviceServiceQueue", deviceServiceQueue);
	}

	@Override
	public int deleteDeviceServiceQueue(Map<String, String> map) {
		return getSqlSession().delete("deviceServiceQueue.deleteDeviceServiceQueue",map);
	}

	
	
	
	

}
