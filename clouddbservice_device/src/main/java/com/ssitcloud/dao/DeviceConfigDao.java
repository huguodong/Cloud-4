package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

public interface DeviceConfigDao {

	int insertDeviceConfig(DeviceConfigEntity deviceConfig);

	int updateDeviceConfig(DeviceConfigEntity deviceConfig);

	List<DeviceConfigEntity> selectList(DeviceConfigEntity deviceConfig);

	int deleteDeviceConfig(DeviceConfigEntity deviceConfig);

	List<DeviceConfigEntity> queryDeviceConfigByDevIdAndLibIdx(Map<String, Object> params);

	List<DeviceConfigEntity> queryDeviceConfigByDeviceIdx(DeviceConfigEntity deviceConfig);

	List<DeviceConfigEntity> queryDeviceConfigOldByDeviceIdx(DeviceConfigEntity deviceConfig);

	int deleteDeviceConfigOldByIdx(DeviceConfigEntity deviceConfig);
	/**
	 * 
	 * @param devIdxList 逗号分割的 idx
	 * @return
	 */
	List<DeviceConfigEntity> queryDeviceConfigInDeviceIdxArr(List<Integer> devIdxList);

	/**
	 * 更新deviceconfig的图书馆信息
	 *
	 * <p>2016年9月23日 上午10:16:57 
	 * <p>create by hjc
	 * @param paramMap
	 * @return
	 */
	public abstract int updateDeviceConfigLibrary(Map<String, Object> param);
	/**
	 * 根据Library_idx 查询 device_config信息
	 * @param deviceConfigEntity
	 * @return
	 */
	List<DeviceConfigEntity> queryDeviceConfigByLibraryIdx(DeviceConfigEntity deviceConfigEntity);

}
