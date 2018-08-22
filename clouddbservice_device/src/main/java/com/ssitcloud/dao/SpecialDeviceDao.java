package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.devmgmt.entity.SpecialDeviceEntity;
import com.ssitcloud.devmgmt.entity.SpecialDevicePageEntity;

public interface SpecialDeviceDao {
	
	public int addSpecialDevice(SpecialDeviceEntity specialDeviceEntity);
	public SpecialDevicePageEntity querySpecialDeviceByPage(SpecialDevicePageEntity entity);
	public List<SpecialDeviceEntity> querySpecialDeviceByParams(Map<String, String> map);
	public int editSpecialDevice(SpecialDeviceEntity deviceEntity);
	public int deleteSpecialDevice(Map<String, String>map);
	/**
	 * 根据serviceid获取特殊设备（3D导航、人流量、智能家具）服务器分组
	 */
	public Map<Integer, String> querySpecialDeviceIdbyServiceIdx();
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx及获取消息队列 设备配置信息
	 * @param map
	 * @return
	 */
	public List<SpecialDeviceEntity> querySpecialDeviceInfobyServiceIdxAndDeviceId(Map<String, String> map);
	
	/**查询数量*/
	public int selectCountSpecialDevice(Map<String, String> map); 
}
