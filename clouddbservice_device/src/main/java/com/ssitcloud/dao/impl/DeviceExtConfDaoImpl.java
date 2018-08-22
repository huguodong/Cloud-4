package com.ssitcloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceExtConfDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.DeviceExtTempEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.entity.page.DeviceExtLogicPageEntity;
import com.ssitcloud.system.entity.DeviceExtConfigRemoteEntity;

@Repository
public class DeviceExtConfDaoImpl extends CommonDaoImpl implements DeviceExtConfDao {
	@Override
	public int insertDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)
			return 0;
		return getSqlSession().insert(
				"deviceExtConf.insertDeviceExtConfig", deviceExtConfig);
	}

	@Override
	public int insertDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)
			return 0;
		return getSqlSession().insert(
				"deviceExtConf.insertDeviceExtTemp", deviceExtTemp);
	}

	@Override
	public int deleteDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)
			return 0;
		return getSqlSession().delete(
				"deviceExtConf.deleteDeviceExtConfig", deviceExtConfig);
	}

	@Override
	public int deleteDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)
			return 0;
		return getSqlSession().delete(
				"deviceExtConf.deleteDeviceExtTemp", deviceExtTemp);
	}

	@Override
	public int updateDeviceExtConfig(DeviceExtConfigEntity deviceExtConfig) {
		if (deviceExtConfig == null)
			return 0;
		return update("deviceExtConf.updateDeviceExtConfig",
				deviceExtConfig);
	}

	@Override
	public int updateDeviceExtTemp(DeviceExtTempEntity deviceExtTemp) {
		if (deviceExtTemp == null)
			return 0;
		return update("deviceExtConf.updateDeviceExtTemp",
				deviceExtTemp);
	}

	@Override
	public DeviceExtConfigEntity selectOne(DeviceExtConfigEntity deviceExtConfig) {
		return getSqlSession().selectOne(
				"deviceExtConf.queryDeviceExtConfig", deviceExtConfig);
	}

	@Override
	public List<DeviceExtConfigEntity> selectList(
			DeviceExtConfigEntity deviceExtConfig) {
		return getSqlSession().selectList(
				"deviceExtConf.queryDeviceExtConfig", deviceExtConfig);
	}
	@Override
	public List<DeviceExtConfigEntity> queryDeviceExtConfigAndExtModel(
			DeviceExtConfigEntity deviceExtConfig) {
		return getSqlSession().selectList(
				"deviceExtConf.queryDeviceExtConfigAndExtModel", deviceExtConfig);
	}
	
	@Override
	public DeviceExtTempEntity selectOne(DeviceExtTempEntity deviceExtTemp) {
		return getSqlSession().selectOne(
				"deviceExtConf.selectDeviceExtTemp", deviceExtTemp);
	}

	@Override
	public List<DeviceExtTempEntity> selectList(
			DeviceExtTempEntity deviceExtTemp) {
		return getSqlSession().selectList(
				"deviceExtConf.selectDeviceExtTemp", deviceExtTemp);
	}

	@Override
	public int insertMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(
				"deviceExtConf.insertMetaExtModel", metadataExtModel);
	}

	@Override
	public int updateMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		if (metadataExtModel == null)
			return 0;
		return getSqlSession().update(
				"deviceExtConf.updateMetaExtModel", metadataExtModel);
	}

	@Override
	public int deleteMetadataExtModel(MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		if (metadataExtModel == null)
			return 0;
		return getSqlSession().update(
				"deviceExtConf.deleteMetaExtModel", metadataExtModel);
	}

	@Override
	public MetadataExtModelEntity selectone(
			MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return getSqlSession().selectOne(
				"deviceExtConf.selectMetaExtModel", metadataExtModel);
	}

	@Override
	public List<MetadataExtModelEntity> selectList(
			MetadataExtModelEntity metadataExtModel) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(
				"deviceExtConf.selectMetaExtModel", metadataExtModel);
	}

	@Override
	public int updateDeviceExtConfig(Map<String, Object> params) {
		
		return getSqlSession().update("deviceExtConf.updateDeviceExtConfigByDeviceUser", params);
	}

	@Override
	public List<Map<String, Object>> selExtDetailListByIdx(Integer extTempId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ext_tpl_id", extTempId);
		return  this.sqlSessionTemplate.selectList("deviceExtConf.selExtDetailListByIdx", param);
	}
	
	@Override
	public List<DeviceExtTempEntity> selectListByDeviceType(
			DeviceExtTempEntity deviceExtTemp) {
		return getSqlSession().selectList(
				"deviceExtConf.selectDeviceExtTempByDeviceType", deviceExtTemp);
	}
	
	
	
	@Override
	public List<DeviceExtConfigRemoteEntity> selectListByRemoteDevice(DeviceExtConfigEntity deviceExtConfig){
		return getSqlSession().selectList("deviceExtConf.selectListByRemoteDevice", deviceExtConfig);
	}

	@Override
	public DeviceExtLogicPageEntity SelExtTempListByParam(DeviceExtLogicPageEntity pageEntity) {
		
		return this.queryDatagridPage(pageEntity, "deviceExtConf.selExtTempListByParam");
	}

	
	@Override
	public int countUsingExtTempplate(DeviceConfigEntity deviceConfigEntity) {
		return this.sqlSessionTemplate.selectOne("deviceExtConf.countUsingExtTempplate",deviceConfigEntity);
	}
	
	@Override
	public List<Map<String, Object>> selExtDataListByIdx(Integer device_idx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("device_idx", device_idx);
		return getSqlSession().selectList("deviceExtConf.selExtDataListByIdx", param);
	}

	@Override
	public Integer selectDataTypeCount(DeviceExtConfigEntity queryDeviceExt) {
		return getSqlSession().selectOne("deviceExtConf.selectDataTypeCount", queryDeviceExt);
	}

	@Override
	public int countDeviceExtTempById(DeviceExtTempEntity deviceExtTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceExtConf.countDeviceExtTempById", deviceExtTempEntity);
	}

	@Override
	public int countDeviceExtTempByDesc(DeviceExtTempEntity deviceExtTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceExtConf.countDeviceExtTempByDesc", deviceExtTempEntity);
	}

	@Override
	public List<DeviceExtConfigEntity> selectByDeviceMonitor(
			DeviceExtConfigEntity deviceExtConfig) {
		return this.sqlSessionTemplate.selectList("deviceExtConf.selectByDeviceMonitor", deviceExtConfig);
	}

	@Override
	public int updateDeviceExtLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("deviceExtConf.updateDeviceExtLibrary",param);
	}

	@Override
	public List<DeviceExtConfigEntity> selectDeviceExtModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceExtConf.selectDeviceExtModalByLibraryIdx",params);
	}

	@Override
	public List<DeviceExtTempEntity> selectDeviceExtTempModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceExtConf.selectDeviceExtTempModalByLibraryIdx",params);
	}

	@Override
	public int insertDeviceExtTempWithIdx(DeviceExtTempEntity ddJson) {
		return sqlSessionTemplate.insert("deviceExtConf.insertDeviceExtTempWithIdx", ddJson);
	}
	
	
}
