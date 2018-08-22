package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DevMonConfMetadataLogObjEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.DeviceMonitorEntity;
import com.ssitcloud.entity.page.DeviceMonTempPageEntity;

public interface DeviceMonConfDao {

	int insertDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig);

	int updateDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig);

	int deleteDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig);

	DeviceMonConfigEntity selectOne(DeviceMonConfigEntity deviceMonConfig);

	List<DeviceMonConfigEntity> selectList(DeviceMonConfigEntity deviceMonConfig);
	
	List<Map<String, Object>> selectmonitor_infoByidx(int monitorTempId);
	/**
	 * 返回idx
	 * 
	 * @param deviceMonTemp
	 * @return
	 */
	int insertDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);

	int updateDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);

	int deleteDeviceMonTemp(DeviceMonTempEntity deviceMonTemp);

	DeviceMonTempEntity selectOne(DeviceMonTempEntity deviceMonTemp);

	List<DeviceMonTempEntity> selectList(DeviceMonTempEntity deviceMonTemp);
	
	List<DeviceMonTempEntity> selectTempBytype(DeviceMonTempEntity deviceMonTemp);
	//DeviceMonConfigPageEntity selectPage(DeviceMonConfigPageEntity deviceMonConfigPage);

	DeviceMonTempPageEntity selectPage(DeviceMonTempPageEntity deviceMonTempPage);

	List<DevMonConfMetadataLogObjEntity> SelDevMonConfMetaLogObjByDeviceMonTplIdx(
			DevMonConfMetadataLogObjEntity devMonConfMetadataLogObj);
	/**
	 * 精确查找某个监控配置信息
	 * @param deviceMonConfig
	 * @return
	 */
	List<DeviceMonConfigEntity> setDeviceMonConfigExactly(
			DeviceMonConfigEntity deviceMonConfig);
	
	Integer selectDataTypeCount(DeviceMonConfigEntity deviceMonConfig);
	/**
	 * 
	 * @param deviceMonConfig
	 * @return
	 */
	List<DeviceMonitorEntity> selectForMonitor(DeviceMonConfigEntity deviceMonConfig);

	
	/**
	 * 修改monitor config表中设备的 所属馆id
	 *
	 * <p>2016年9月23日 上午9:49:23 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceMonLibrary(Map<String, Object> param);

	List<DeviceMonConfigEntity> selectDeviceMonModalByLibraryIdx(Map<String, Object> params);

	List<DeviceMonTempEntity> selectDeviceMonTempModalByLibraryIdx(Map<String, Object> params);

	int insertDeviceMonTempWithIdx(DeviceMonTempEntity ddJson);

}
