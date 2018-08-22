package com.ssitcloud.interfaceconfig.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.devmgmt.entity.ConfigDataEntity;
import com.ssitcloud.devmgmt.entity.ConfigDataEntityF;
import com.ssitcloud.devmgmt.entity.DataEntity;
import com.ssitcloud.devmgmt.entity.DeviceType;
import com.ssitcloud.devmgmt.entity.QueryDevice;
import com.ssitcloud.interfaceconfig.dao.JsonDataDao;

@Repository
public class JsonDataDaoImpl extends CommonDaoImpl implements JsonDataDao {

	public List<DataEntity> getJsonData(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("json.jsonData", map);
	}

	public int saveJsonData(ConfigDataEntity entity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("json.saveData", entity);
	}

	public String getInitData(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("json.getInitData", map);
	}

	public int queryExsit(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("json.queryExsit", map);
	}

	@Override
	public int updateJsonData(ConfigDataEntity entity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("json.udpateData", entity);
	}

	@Override
	public List<QueryDevice> queryAllLibDev(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("json.queryDevice", map);
	}

	@Override
	public int queryLibExsit(Map<String, String> map) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("json.queryLibExsit", map);
	}

	@Override
	public List<DeviceType> getDevTypeData(String library_idx) {
		return this.sqlSessionTemplate.selectList("json.getDevTypeData", Integer.valueOf(library_idx));
	}

	@Override
	public List<DataEntity> getViewConfigData() {
		List<DataEntity> devconfs = this.sqlSessionTemplate.selectList("json.getAllViewConfigData");
		return devconfs;
	}

	@Override
	public List<ConfigDataEntity> getConfigDataEntity(HashMap<String, Object> map) {
		List<ConfigDataEntity> configs = this.sqlSessionTemplate.selectList("json.getAllConfigData", map);
		return configs;
	}
	
	@Override
	public List<DataEntity> queryViewConfigData() {
		return this.sqlSessionTemplate.selectList("json.selectAllViewConfig");
	}

	@Override
	public List<ConfigDataEntityF> queryConfigDataByDevIdAndLibIdx(Map<String, Object> params) {
		return getSqlSession().selectList(
				"json.selectConfigDataByDevIdAndLibIdx", params);
	}

	@Override
	public List<ConfigDataEntity> selectDeviceIdxByLibId(Map<String, Object> params) {
		return getSqlSession().selectList(
				"json.selectDeviceIdxByLibId", params);
	}
	
	
}
