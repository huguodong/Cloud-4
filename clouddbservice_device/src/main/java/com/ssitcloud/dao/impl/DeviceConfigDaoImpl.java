package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceConfigDao;
import com.ssitcloud.entity.DeviceConfigEntity;

@Repository
public class DeviceConfigDaoImpl extends CommonDaoImpl implements DeviceConfigDao{
	@Override
	public int insertDeviceConfig(DeviceConfigEntity deviceConfig) {
		if (deviceConfig == null)
			return 0;
		return getSqlSession().insert("deviceConfig.insertDeviceConfig", deviceConfig);
	}

	@Override
	public int updateDeviceConfig(DeviceConfigEntity deviceConfig) {
		return getSqlSession().update(
				"deviceConfig.updateDeviceConfig", deviceConfig);
	}

	@Override
	public List<DeviceConfigEntity> selectList(DeviceConfigEntity deviceConfig) {
		return getSqlSession().selectList(
				"deviceConfig.queryDeviceConfig", deviceConfig);
	}

	@Override
	public int deleteDeviceConfig(DeviceConfigEntity deviceConfig) {
		if (deviceConfig == null)
			return 0;
		return getSqlSession().delete(
				"deviceConfig.deleteDeviceConfig", deviceConfig);
	}

	@Override
	public List<DeviceConfigEntity> queryDeviceConfigByDevIdAndLibIdx(
			Map<String, Object> params) {
		return getSqlSession().selectList(
				"deviceConfig.queryDeviceConfigByDevIdAndLibIdx", params);
	}

	@Override
	public List<DeviceConfigEntity> queryDeviceConfigByDeviceIdx(
			DeviceConfigEntity deviceConfig) {
		return getSqlSession().selectList("deviceConfig.queryDeviceConfigByDeviceIdx", deviceConfig);
	}

	@Override
	public List<DeviceConfigEntity> queryDeviceConfigOldByDeviceIdx(
			DeviceConfigEntity deviceConfig) {
		return getSqlSession().selectList("deviceConfig.queryDeviceConfigOldByDeviceIdx", deviceConfig);
	}

	@Override
	public int deleteDeviceConfigOldByIdx(DeviceConfigEntity deviceConfig) {
		return getSqlSession().delete("deviceConfig.deleteDeviceConfigOldByIdx", deviceConfig);
	}
	@Override
	public List<DeviceConfigEntity> queryDeviceConfigInDeviceIdxArr(List<Integer> devIdxList){
		return getSqlSession().selectList("deviceConfig.queryDeviceConfigInDeviceIdxArr", devIdxList);
	}

	@Override
	public int updateDeviceConfigLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("deviceConfig.updateDeviceConfigLibrary",param);
	}

	@Override
	public List<DeviceConfigEntity> queryDeviceConfigByLibraryIdx(
			DeviceConfigEntity deviceConfigEntity) {
		return getSqlSession().selectList("deviceConfig.queryDeviceConfigByLibraryIdx", deviceConfigEntity);
	}

	

}
