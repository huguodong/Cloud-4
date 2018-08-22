package com.ssitcloud.dao.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceRunConfDao;
import com.ssitcloud.entity.DeviceConfigEntity;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.DeviceRunTempEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.entity.page.DeviceRunTempPageEntity;
import com.ssitcloud.system.entity.DeviceRunConfigRemoteEntity;

@Repository(value="deviceRunConfDao")
public class DeviceRunConfDaoImpl extends CommonDaoImpl implements DeviceRunConfDao{
	
	
	@Override
	public int insertDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return getSqlSession().insert("deviceRunConfig.insertDevRunConf", deviceRunConfig);
	}

	@Override
	public int insertBatchDeviceRunConfig(
			List<DeviceRunConfigEntity> deviceRunConfigs) {
		SqlSession sqlSession = getSqlSession();
		int res = 0;
		for (DeviceRunConfigEntity d : deviceRunConfigs) {
			res += sqlSession.insert("deviceRunConfig.insertDevRunConf", d);
		}
		return res;
	}

	@Override
	public int updateDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return getSqlSession().update("deviceRunConfig.updateDevRunConf", deviceRunConfig);
	}

	@Override
	public int deleteDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig) {
		return getSqlSession().delete("deviceRunConfig.deleteDevRunConf", deviceRunConfig);
	}

	

	@Override
	public List<DeviceRunConfigEntity> selectList(DeviceRunConfigEntity deviceRunConfig) {
		SqlSession sqlsession=getSqlSession();
		return sqlsession.selectList("deviceRunConfig.queryDeviceRunConfig", deviceRunConfig);
	}
	
	@Override
	public List<DeviceRunConfigEntity> queryDeviceRunConfigAndMetadataRunConfig(DeviceRunConfigEntity deviceRunConfig){
		return getSqlSession().selectList("deviceRunConfig.queryDeviceRunConfigAndMetadataRunConfig", deviceRunConfig);
	}
	
	
	/**
	 * queryDeviceRunConfigByDown
	 * @return
	 */
	@Override
	public List<DeviceRunConfigRemoteEntity> queryDeviceRunConfigByDown(DeviceRunConfigEntity deviceRunConfig){
		return getSqlSession().selectList("deviceRunConfig.queryDeviceRunConfigByDown", deviceRunConfig);
	}

	
	@Override
	public int insertDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return getSqlSession().insert(
				"deviceRunConfig.insertDevRunTemp", deviceRunTempEntity);
	}

	@Override
	public int updateDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return getSqlSession().update(
				"deviceRunConfig.updateDevRunTemp", deviceRunTempEntity);
	}

	@Override
	public int deleteDeviceRunTemp(DeviceRunTempEntity deviceRunTempEntity) {
		return getSqlSession().delete(
				"deviceRunConfig.deleteDevRunTemp", deviceRunTempEntity);
	}



	@Override
	public List<DeviceRunTempEntity> selectList(
			DeviceRunTempEntity deviceRunTempEntity) {
		return getSqlSession().selectList(
				"deviceRunConfig.selectDevRunTemp", deviceRunTempEntity);
	}
	@Override
	public List<DeviceRunTempEntity> selectListByDeviceType(
			DeviceRunTempEntity deviceRunTempEntity) {
		return getSqlSession().selectList(
				"deviceRunConfig.selectDevRunTempByDeviceType", deviceRunTempEntity);
	}

	@Override
	public int insertMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		// TODO Auto-generated method stub
		return getSqlSession().insert(
				"deviceRunConfig.insertMetaRunConf", metaRunConfigEntity);
	}

	@Override
	public int updateMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		// TODO Auto-generated method stub
		return getSqlSession().update(
				"deviceRunConfig.updateMetaRunConf", metaRunConfigEntity);
	}

	@Override
	public int deleteMetaRunConfig(MetaRunConfigEntity metaRunConfigEntity) {
		// TODO Auto-generated method stub
		return getSqlSession().delete(
				"deviceRunConfig.deleteMetaRunConf", metaRunConfigEntity);
	}

	

	@Override
	public List<MetaRunConfigEntity> selectList(
			MetaRunConfigEntity metaRunConfigEntity) {
		// TODO Auto-generated method stub
		return getSqlSession().selectList(
				"deviceRunConfig.queryMetaRunConf", metaRunConfigEntity);
	}

	@Override
	public int updateDeviceRunConfig(Map<String, Object> params) {
		return getSqlSession().update("deviceRunConfig.updDeviceRunConfByRemoteDevice", params);
	}

	@Override
	public List<Map<String, Object>> selRunDetailListByIdx(Integer runTempIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("device_tpl_idx", runTempIdx);
		return this.sqlSessionTemplate.selectList("deviceRunConfig.selRunDetailListByIdx",param);
	}

	@Override
	public List<Map<String, Object>> selRunDataListByIdx(Integer runTempIdx) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("device_idx", runTempIdx);
		return this.sqlSessionTemplate.selectList("deviceRunConfig.selRunDataListByIdx",param);
	}

	@Override
	public DeviceRunConfigEntity selectOneDeviceRunConfigByExtIdLimitOne(
			DeviceRunConfigEntity deviceRunConfigEntity) {
		return this.sqlSessionTemplate.selectOne("deviceRunConfig.selectOneDeviceRunConfigByExtIdLimitOne", deviceRunConfigEntity);
	}

	@Override
	public int deleteRunDataByidxAndtype(
			DeviceRunConfigEntity deviceRunConfigEntity) {
		
		return this.sqlSessionTemplate.delete("deviceRunConfig.deleteRunConfByidxAndtype", deviceRunConfigEntity);
	}

	@Override
	public DeviceRunTempPageEntity selRunTempListByParam(DeviceRunTempPageEntity pageEntity) {
		return this.queryDatagridPage(pageEntity, "deviceRunConfig.selRunTempListByParam");
	}

	@Override
	public int countUsingRunTempplate(DeviceConfigEntity deviceConfigEntity) {
		return this.sqlSessionTemplate.selectOne("deviceRunConfig.countUsingRunTempplate",deviceConfigEntity);
	}

	@Override
	public Integer selectDataTypeCount(DeviceRunConfigEntity deviceRunConfig) {
		
		return this.sqlSessionTemplate.selectOne("deviceRunConfig.selectDataTypeCount",deviceRunConfig);
	}

	@Override
	public int countDeviceRunTempById(DeviceRunTempEntity deviceRunTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceRunConfig.countDeviceRunTempById", deviceRunTempEntity);
	}

	@Override
	public int countDeviceRunTempByDesc(DeviceRunTempEntity deviceRunTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceRunConfig.countDeviceRunTempByDesc", deviceRunTempEntity);
	}

	@Override
	public int updateDeviceRunLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("deviceRunConfig.updateDeviceRunLibrary",param);
	}

	@Override
	public List<DeviceRunConfigEntity> selectDeviceRunModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceRunConfig.selectDeviceRunModalByLibraryIdx",params);
	}

	@Override
	public List<DeviceRunTempEntity> selectDeviceRunTempModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceRunConfig.selectDeviceRunTempModalByLibraryIdx",params);
	}

	@Override
	public int insertDeviceRunTempWithIdx(DeviceRunTempEntity ddJson) {
		return sqlSessionTemplate.insert("deviceRunConfig.insertDeviceRunTempWithIdx", ddJson);
	}
	
	
	
	
	

}
