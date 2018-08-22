package com.ssitcloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.dao.DeviceDBSyncConfDao;
import com.ssitcloud.entity.DeviceDBSyncConfigEntity;
import com.ssitcloud.entity.DeviceDBSyncTempEntity;
import com.ssitcloud.entity.page.DBSyncTempPageEntity;
import com.ssitcloud.system.entity.DeviceDBSyncConfigRemoteEntity;

@Repository
public class DeviceDBSyncConfDaoImpl extends CommonDaoImpl implements DeviceDBSyncConfDao {

	@Override
	public int insertDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession()
				.insert("deviceDBSyncConf.insertDeviceDBSyncConf", deviceDBSyncConfig);
	}

	@Override
	public int updateDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession()
				.update("deviceDBSyncConf.updateDeviceDBSyncConf", deviceDBSyncConfig);
	}

	@Override
	public int deleteDeviceDBSyncConf(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession()
				.delete("deviceDBSyncConf.deleteDeviceDBSyncConf", deviceDBSyncConfig);
	}

	@Override
	public List<DeviceDBSyncConfigEntity> selectDeviceDBSyncConfig(
			DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession().selectList("deviceDBSyncConf.selectDeviceDBSyncConfExcatly",
				deviceDBSyncConfig);
	}

	@Override
	public List<DeviceDBSyncConfigEntity> selectList(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession().selectList("deviceDBSyncConf.selectDeviceDBSyncConf",
				deviceDBSyncConfig);
	}

	@Override
	public int insertDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		return getSqlSession().insert("deviceDBSyncConf.insertDeviceDBSyncTemp", deviceDBSyncTemp);
	}

	@Override
	public int updateDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		return getSqlSession().update("deviceDBSyncConf.updateDeviceDBSyncTemp", deviceDBSyncTemp);
	}

	@Override
	public int deleteDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		return getSqlSession().delete("deviceDBSyncConf.deleteDeviceDBSyncTemp", deviceDBSyncTemp);
	}

	@Override
	public DeviceDBSyncTempEntity selectDeviceDBSyncTemp(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		// 查询单个一般是通过主键查询
		return getSqlSession().selectOne("deviceDBSyncConf.selectDeviceDBSyncTemp",
				deviceDBSyncTemp);
	}

	@Override
	public List<DeviceDBSyncTempEntity> selectList(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		return getSqlSession().selectList("deviceDBSyncConf.selectDeviceDBSyncTemp",
				deviceDBSyncTemp);
	}
	@Override
	public List<DeviceDBSyncTempEntity> selectListByDeviceType(DeviceDBSyncTempEntity deviceDBSyncTemp) {
		return getSqlSession().selectList("deviceDBSyncConf.selectDeviceDBSyncTempByDeviceType",
				deviceDBSyncTemp);
	}

	@Override
	public List<DeviceDBSyncConfigRemoteEntity> selectListByRemoteDeviceSync(DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return getSqlSession().selectList("deviceDBSyncConf.selectListByRemoteDeviceSync", deviceDBSyncConfig);
	}

	@Override
	public List<Map<String, Object>> selDbsyncDetailListByIdx(
			Integer dbsyncTempId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dbsyncid", dbsyncTempId);
		return  this.sqlSessionTemplate.selectList("deviceDBSyncConf.selDbsyncDetailListByIdx", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DBSyncTempPageEntity seldbsyncTemp(
			DBSyncTempPageEntity dbSyncTempPageEntity) {
		DBSyncTempPageEntity total = this.sqlSessionTemplate.selectOne("deviceDBSyncConf.selDBSyncid", dbSyncTempPageEntity);
		dbSyncTempPageEntity.setTotal(total.getTotal());
		dbSyncTempPageEntity.setDoAount(false);
		dbSyncTempPageEntity.setRows(this.sqlSessionTemplate.selectList("deviceDBSyncConf.selDBSyncid", dbSyncTempPageEntity));
		return dbSyncTempPageEntity;
	}

	@Override
	public List<DeviceDBSyncConfigEntity> seldbsynctempconfigByidx(int idx) {
		
		return this.sqlSessionTemplate.selectList("deviceDBSyncConf.selDbsynctempconfigByIdx", idx);
	}

	@Override
	public DeviceDBSyncTempEntity seldbsynctempByidx(int idx) {
		
		return this.sqlSessionTemplate.selectOne("deviceDBSyncConf.selDBSyncTempByidx", idx);
	}

	@Override
	public int deleteDBSyncTemp(Integer idx) {
		
		return this.sqlSessionTemplate.delete("deviceDBSyncConf.deleteDeviceDBSyncTemp", idx);
	}

	@Override
	public Integer selectDataTypeCount(
			DeviceDBSyncConfigEntity deviceDBSyncConfig) {
		return this.sqlSessionTemplate.selectOne("deviceDBSyncConf.selectDataTypeCount", deviceDBSyncConfig);
	}

	@Override
	public int countDeviceDBSyncTempById(DeviceDBSyncTempEntity dbSyncTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceDBSyncConf.countDeviceDBSyncTempById",dbSyncTempEntity);
	}

	@Override
	public int countDeviceDBSyncTempByName(DeviceDBSyncTempEntity dbSyncTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceDBSyncConf.countDeviceDBSyncTempByName",dbSyncTempEntity);
	}

	@Override
	public int countUsingDBSyncTempplate(DeviceDBSyncTempEntity dbSyncTempEntity) {
		return this.sqlSessionTemplate.selectOne("deviceDBSyncConf.countUsingDBSyncTempplate",dbSyncTempEntity);
	}

	@Override
	public int updateDeviceDBsyncLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("deviceDBSyncConf.updateDeviceDBsyncLibrary",param);
	}

	@Override
	public List<DeviceDBSyncConfigEntity> selectDeviceDBSyncModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceDBSyncConf.selectDeviceDBSyncModalByLibraryIdx",params);
	}

	@Override
	public List<DeviceDBSyncTempEntity> seldbsyncTempModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceDBSyncConf.seldbsyncTempModalByLibraryIdx",params);
	}

	@Override
	public int insertDeviceDBSyncTempWithIdx(DeviceDBSyncTempEntity ddJson) {
		return sqlSessionTemplate.insert("deviceDBSyncConf.insertDeviceDBSyncTempWithIdx", ddJson);
	}
	
	
	


}
