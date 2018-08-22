package com.ssitcloud.service;

import java.util.List;
import java.util.Map;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.page.DeviceExtLogicPageEntity;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

public interface DeviceExtConfService extends CommonDao{

	// 表device_ext_config增删改查
	int insertDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);

	int deleteDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);

	int updateDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig);

	DeviceExtConfigEntity selectOne(DeviceExtConfigEntity deviceExtConfig);

	List<DeviceExtConfigEntity> selectList(DeviceExtConfigEntity deviceExtConfig);

	// 表device_ext_template增删改查
	int updateDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	int deleteDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	int insertDeviceExtTemp(DeviceExtTempEntity deviceExtTemp);

	DeviceExtTempEntity selectOne(DeviceExtTempEntity deviceExtTemp);

	List<DeviceExtTempEntity> selectList(DeviceExtTempEntity deviceExtTemp);

	// 表metadata_ext_model增删改查
	int insertMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	int updateMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	int deleteMetadataExtModel(MetadataExtModelEntity metadataExtModel);

	MetadataExtModelEntity selectone(MetadataExtModelEntity metadataExtModel);

	List<MetadataExtModelEntity> selectList(
			MetadataExtModelEntity metadataExtModel);
	/**
	 * 获取所有的硬件与逻辑配置模板
	 *
	 * <p>2016年4月25日 下午6:56:44
	 * <p>create by hjc
	 * @return
	 */
	public abstract List<Map<String, Object>> selAllExtTempList(Map<String, String> param);	
	
	/**
	 * 根据条件获取硬件与逻辑配置模板list的分页信息
	 *
	 * <p>2016年5月19日 下午3:21:19 
	 * <p>create by hjc
	 * @param pageEntity
	 * @param param
	 * @return
	 */
	public abstract DeviceExtLogicPageEntity SelExtTempListByParam(DeviceExtLogicPageEntity pageEntity);

	
	public abstract List<Map<String, Object>> SelExtData(String json);
	
	/**
	 * 新增模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param extConfStr
	 * @return
	 */
	public abstract ResultEntity addExtTemp(String extConfStr);
	
	/**
	 * 更新模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param extConfStr
	 * @return
	 */
	public abstract ResultEntity updateExtTemp(String extConfStr);
	
	/**
	 * 删除模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param idx
	 * @return
	 */
	public abstract ResultEntity delExtTemp(String idx);
	
	/**
	 * 删除模板数据
	 *
	 * <p>2016年6月16日 上午9:34:44 
	 * <p>create by hjc
	 * @param req
	 * @return
	 */
	public abstract ResultEntity delMultiExtTemp(String req);
	
	/**
	 *  获取设备对应的外设信息
	 * @param req
	 * @return
	 */
	public abstract ResultEntity GetDevExtModel(String req);
	
	public List<DeviceExtConfigRemoteEntity> getDeviceExtConfig(DeviceConfigEntity configEntity);
}
