package com.ssitcloud.service;


import com.ssitcloud.common.entity.ResultEntity;

/**
 *3D导航、人流量、智能家具服务消息队列service
 * @author yeyalin
 *
 */
public interface DeviceServiceQueueService {
	
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return ResultEntity
	 */
	public ResultEntity queryQueueIdbyServiceIdx() ;

}
