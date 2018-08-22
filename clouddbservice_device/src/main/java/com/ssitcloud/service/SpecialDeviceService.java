package com.ssitcloud.service;


import com.ssitcloud.common.entity.ResultEntity;

public interface SpecialDeviceService {
	
	public ResultEntity addSpecialDevice(String req);
	public ResultEntity querySpecialDeviceByPage(String req);
	public ResultEntity querySpecialDeviceByParams(String req);
	public ResultEntity editSpecialDevice(String req);
	public ResultEntity deleteSpecialDevice(String req);
	/**
	 * 根据serviceid获取特殊设备（3D导航、人流量、智能家具）服务器分组
	 * @return
	 */
	public ResultEntity querySpecialDeviceIdbyServiceIdx();
}
