package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.SpecialDeviceDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceQueue;
import com.ssitcloud.devmgmt.entity.SpecialDeviceEntity;
import com.ssitcloud.devmgmt.entity.SpecialDevicePageEntity;
@Repository
public class SpecialDeviceDaoImpl extends CommonDaoImpl implements SpecialDeviceDao{

	public int addSpecialDevice(SpecialDeviceEntity specialDeviceEntity) {
		return getSqlSession().insert("specialDevice.addSpecialDevice",specialDeviceEntity);
	}
	
	
	public SpecialDevicePageEntity querySpecialDeviceByPage(SpecialDevicePageEntity entity){
		
		SpecialDevicePageEntity devicePageEntity = getSqlSession().selectOne("specialDevice.querySpecialDeviceByPage",entity);
		entity.setTotal(devicePageEntity == null?0:devicePageEntity.getTotal());
		entity.setDoAount(false);
		List<SpecialDevicePageEntity> deviceEntities = getSqlSession().selectList("specialDevice.querySpecialDeviceByPage",entity);
		entity.setRows(deviceEntities);
		return entity;
	}
	
	public List<SpecialDeviceEntity> querySpecialDeviceByParams(Map<String, String> map){
		
		return getSqlSession().selectList("specialDevice.querySpecialDeviceByParams",map);
	}


	@Override
	public int editSpecialDevice(SpecialDeviceEntity deviceEntity) {
		return getSqlSession().update("specialDevice.editSpecialDevice",deviceEntity);
	}

	@Override
	public int deleteSpecialDevice(Map<String, String> map) {
		return getSqlSession().delete("specialDevice.deleteSpecialDevice",map);
	}
	
	
	/**
	 * 根据serviceid获取特殊设备（3D导航、人流量、智能家具）服务器分组
	 * @return Map<service_idx, queue_id>
	 */
	@Override
	public Map<Integer, String> querySpecialDeviceIdbyServiceIdx(){
		return getSqlSession().selectMap("specialDevice.querySpecialDeviceIdbyServiceIdx", "service_idx");
	}
	
	/**
	 * 根据特殊设备（3D导航、人流量、智能家具）服务器serviceidx及获取消息队列 设备配置信息
	 * @param map
	 * @return
	 */
	@Override
	public List<SpecialDeviceEntity> querySpecialDeviceInfobyServiceIdxAndDeviceId(Map<String, String> map){
		return getSqlSession().selectList("specialDevice.querySpecialDeviceInfobyServiceIdxAndDeviceId", map);
	}


	@Override
	public int selectCountSpecialDevice(Map<String, String> map) {
		return getSqlSession().selectOne("specialDevice.selectCountSpecialDevice", map);
	}
	
	
}
