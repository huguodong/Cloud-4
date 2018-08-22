package com.ssitcloud.dao;

import java.util.List;
import java.util.Map;

import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.page.DeviceExtLogicPageEntity;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

public interface DeviceExtConfDao {

	int insertDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);

	int insertDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	int deleteDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);

	int deleteDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	int updateDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);
	
	int updateDeviceExtConfig(Map<String,Object> params);

	int updateDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	DeviceExtConfigEntity selectOne(DeviceExtConfigEntity deviceExtConfig);

	List<DeviceExtConfigEntity> selectList(DeviceExtConfigEntity deviceExtConfig);

	DeviceExtTempEntity selectOne(DeviceExtTempEntity deviceExtTemp);

	List<DeviceExtTempEntity> selectList(DeviceExtTempEntity deviceExtTemp);

	int insertMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	int updateMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	MetadataExtModelEntity selectone(MetadataExtModelEntity metadataExtModel);

	int deleteMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	List<MetadataExtModelEntity> selectList(MetadataExtModelEntity metadataExtModel);
	
	/**
	 * 根据外设模板id获取外设模板的逻辑设备list
	 *
	 * <p>2016年4月25日 下午7:19:36
	 * <p>create by hjc
	 * @param extTempId
	 * @return
	 */
	public abstract List<Map<String, Object>> selExtDetailListByIdx(Integer extTempId);
	
	/**
	 * 根据device_type获取外设模板list
	 *
	 * <p>2016年5月6日 下午3:53:07 
	 * <p>create by hjc
	 * @param deviceExtTemp
	 * @return
	 */
	public abstract List<DeviceExtTempEntity> selectListByDeviceType(DeviceExtTempEntity deviceExtTemp);
	
	/**
	 * 根据条件获取硬件与逻辑配置模板list的分页信息
	 *
	 * <p>2016年5月19日 下午3:20:49 
	 * <p>create by hjc
	 * @param pageEntity
	 * @return
	 */
	public abstract DeviceExtLogicPageEntity SelExtTempListByParam(DeviceExtLogicPageEntity pageEntity);
	
	

	List<DeviceExtConfigRemoteEntity> selectListByRemoteDevice(
			DeviceExtConfigEntity deviceExtConfig);
	/**
	 * 查询外设 外关联ext_model
	 * @param deviceExtConfig
	 * @return
	 */
	List<DeviceExtConfigEntity> queryDeviceExtConfigAndExtModel(DeviceExtConfigEntity deviceExtConfig);

	public abstract List<Map<String, Object>> selExtDataListByIdx(Integer device_idx);
	
	/**
	 * 查询外设模板是否被使用
	 *
	 * <p>2016年6月17日 下午9:07:05 
	 * <p>create by hjc
	 * @param deviceConfigEntity
	 * @return
	 */
	public abstract int countUsingExtTempplate(DeviceConfigEntity deviceConfigEntity);
	/**
	 * 查询该设备的数据模板条数
	 * 查询必要条件
	 * device_ext_id
	 * device_tpl_type
	 * library_idx
	 * @param queryDeviceExt
	 * @return
	 */
	Integer selectDataTypeCount(DeviceExtConfigEntity queryDeviceExt);
	
	/**
	 * 根据模板id查询是否有相同的
	 *
	 * <p>2016年7月15日 下午6:15:26 
	 * <p>create by hjc
	 * @param deviceExtTempEntity
	 * @return
	 */
	public abstract int countDeviceExtTempById(DeviceExtTempEntity deviceExtTempEntity);
	/**
	 * 根据模板名查询是否有相同的
	 *
	 * <p>2016年7月15日 下午6:15:39 
	 * <p>create by hjc
	 * @param deviceExtTempEntity
	 * @return
	 */
	public abstract int countDeviceExtTempByDesc(DeviceExtTempEntity deviceExtTempEntity);
	/**
	 * 设备监控 查询 当前设备的外设信息
	 * @param deviceExtConfig
	 * @return
	 */
	public List<DeviceExtConfigEntity> selectByDeviceMonitor(DeviceExtConfigEntity deviceExtConfig);
	
	/**
	 * 修改extconfig表中设备的 所属馆id
	 *
	 * <p>2016年9月23日 上午9:49:23 
	 * <p>create by hjc
	 * @param param
	 * @return
	 */
	public abstract int updateDeviceExtLibrary(Map<String, Object> param);

	List<DeviceExtConfigEntity> selectDeviceExtModalByLibraryIdx(
			Map<String, Object> params);

	List<DeviceExtTempEntity> selectDeviceExtTempModalByLibraryIdx(
			Map<String, Object> params);

	int insertDeviceExtTempWithIdx(DeviceExtTempEntity ddJson);
	
}
