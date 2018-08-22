package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceServiceTypeDao;
import com.ssitcloud.devmgmt.entity.DeviceServiceEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceTypeEntity;
import com.ssitcloud.devmgmt.entity.DeviceServiceTypePageEntity;
@Repository
public class DeviceServiceTypeDaoImpl extends CommonDaoImpl implements DeviceServiceTypeDao {

	@Override
	public List<DeviceServiceTypeEntity> queryDeviceServiceTypeByParams(Map<String, String> map) {
		return getSqlSession().selectList("deviceServiceType.queryDeviceServceTypeByParams", map);
	}

	@Override
	public DeviceServiceTypePageEntity queryDeviceServiceTypeByPage(DeviceServiceTypePageEntity entity) {
		DeviceServiceTypePageEntity deviceServiceTypePageEntity = getSqlSession().selectOne("deviceServiceType.queryDeviceServiceTypeByPage", entity);
		entity.setTotal(deviceServiceTypePageEntity == null ? 0 :deviceServiceTypePageEntity.getTotal());
		entity.setDoAount(false);
		List<DeviceServiceEntity> entities = getSqlSession().selectList("deviceServiceType.queryDeviceServiceTypeByPage", entity);
		entity.setRows(entities);
		return entity;
	}

	@Override
	public int addDeviceServiceType(DeviceServiceTypeEntity serviceTypeEntity) {
		return getSqlSession().insert("deviceServiceType.addDeviceServiceType",serviceTypeEntity);
	}

	@Override
	public int deleteDeviceServiceType(Integer service_type_idx) {
		return getSqlSession().delete("deviceServiceType.deleteDeviceServiceType", service_type_idx);
	}

	@Override
	public int editDeviceServiceType(DeviceServiceTypeEntity deviceServiceTypeEntity) {
		return getSqlSession().update("deviceServiceType.editDeviceServiceType", deviceServiceTypeEntity);
	}
	
	
	


}
