package com.ssitcloud.service;


import com.ssitcloud.common.entity.ResultEntity;

/**
 *3D导航、人流量、智能家具服务service
 * @author yeyalin
 *
 */
public interface DeviceServiceService {
	
	public ResultEntity deviceServiceRegister(String req);
	public ResultEntity queryDeviceServiceByPage(String req);
	public ResultEntity queryDeviceServiceByParams(String req);
	public ResultEntity queryDeviceServiceRegister(String req);
	/**更新服务操作日期，触发触发器*/
	public ResultEntity updateDeviceServiceOperateTime(String req);
	
	
	/**
	 * 根据libidx获取特殊设备（3D导航、人流量、智能家具） 分组
	 * @return ResultEntity
	 */
	public ResultEntity queryServiceIdbyLibIdx() ;
	
	public ResultEntity editDeviceServiceRegister(String req);
	public ResultEntity deleteDeviceService(String req);
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx获取服务名称 分组serviceid
	 * @return ResultEntity
	 */
	public ResultEntity queryServiceIdbyServiceIdx() ;
	
	public ResultEntity queryServiceIdbyLibId() ;
	public ResultEntity selectCountDeviceService(String req);

}
