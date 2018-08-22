package com.ssitcloud.dao.impl;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceMonConfDao;
import com.ssitcloud.entity.DevMonConfMetadataLogObjEntity;
import com.ssitcloud.entity.DeviceMonConfigEntity;
import com.ssitcloud.entity.DeviceMonTempEntity;
import com.ssitcloud.entity.DeviceMonitorEntity;
import com.ssitcloud.entity.page.DeviceMonTempPageEntity;

@Repository
public class DeviceMonConfDaoImpl extends CommonDaoImpl implements DeviceMonConfDao{

	@Override
	public int insertDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return getSqlSession().insert("deviceMonConf.insertDeviceMonConf", deviceMonConfig);
	}
	@Override
	public int updateDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return getSqlSession().update("deviceMonConf.updateDeviceMonConf", deviceMonConfig);
	}
	@Override
	public int deleteDeviceMonConfig(DeviceMonConfigEntity deviceMonConfig){
		if(deviceMonConfig==null) return 0;
		return getSqlSession().delete("deviceMonConf.deleteDeviceMonConf", deviceMonConfig);
	}
	@Override
	public DeviceMonConfigEntity selectOne(DeviceMonConfigEntity deviceMonConfig){
		return getSqlSession().selectOne("deviceMonConf.selectDeviceMonConf", deviceMonConfig);
	}
	@Override
	public List<DeviceMonConfigEntity> selectList(DeviceMonConfigEntity deviceMonConfig){
		return getSqlSession().selectList("deviceMonConf.selectDeviceMonConf", deviceMonConfig);
	}
	
	public List<Map<String, Object>> selectmonitor_infoByidx(int monitorTempId){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("monitorId", monitorTempId);
		List<Map<String,Object>> logicobj = getSqlSession().selectList("deviceMonConf.selectmon_logicobj", param);
		List<Map<String,Object>> monitorobj = getSqlSession().selectList("deviceMonConf.selectmon_monitorobj", param);
		List<Map<String,Object>> plcobj = getSqlSession().selectList("deviceMonConf.selectmon_plcobj", param);
		List<Map<String,Object>> timeout = getSqlSession().selectList("deviceMonConf.selectmon_timeout", param);
		logicobj.addAll(plcobj);
		logicobj.addAll(monitorobj);
		logicobj.addAll(timeout);
		return logicobj;
	} 
	
	@Override
	public int insertDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return getSqlSession().insert("deviceMonConf.insertDeviceMonTempEntity", deviceMonTemp);
	}
	@Override
	public int updateDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return getSqlSession().update("deviceMonConf.updateDeviceMonTempEntity", deviceMonTemp);
	}
	@Override
	public int deleteDeviceMonTemp(DeviceMonTempEntity deviceMonTemp){
		if(deviceMonTemp==null) return 0;
		return getSqlSession().delete("deviceMonConf.deleteDeviceMonTempEntity", deviceMonTemp.getDevice_mon_tpl_idx());
	}
	@Override
	public DeviceMonTempEntity selectOne(DeviceMonTempEntity deviceMonTemp){
		return getSqlSession().selectOne("deviceMonConf.queryDeviceMonTempEntity", deviceMonTemp);
	}
	@Override
	public List<DeviceMonTempEntity> selectList(DeviceMonTempEntity deviceMonTemp){
		return getSqlSession().selectList("deviceMonConf.queryDeviceMonTempEntity", deviceMonTemp);
	}
	
	
	@Override
	public DeviceMonTempPageEntity selectPage(DeviceMonTempPageEntity deviceMonTempPage) {
		DeviceMonTempPageEntity total=getSqlSession().selectOne("deviceMonConf.queryDeviceMonitorByParam", deviceMonTempPage);
		deviceMonTempPage.setTotal(total.getTotal());
		deviceMonTempPage.setDoAount(false);
		deviceMonTempPage.setRows(getSqlSession().selectList("deviceMonConf.queryDeviceMonitorByParam", deviceMonTempPage));
		return deviceMonTempPage;
	}
	
	@Override
	public List<DevMonConfMetadataLogObjEntity> SelDevMonConfMetaLogObjByDeviceMonTplIdx(
			DevMonConfMetadataLogObjEntity devMonConfMetadataLogObj) {
		if(devMonConfMetadataLogObj==null){ return null;}
		return getSqlSession().selectList("deviceMonConf.SelDevMonConfMetaLogObjByDeviceMonTplIdx", devMonConfMetadataLogObj);
	}
	
	@Override
	public List<DeviceMonTempEntity> selectTempBytype(DeviceMonTempEntity deviceMonTemp){
		return getSqlSession().selectList("deviceMonConf.queryDeviceMonTempBytype", deviceMonTemp);
	}
	@Override
	public List<DeviceMonConfigEntity> setDeviceMonConfigExactly(
			DeviceMonConfigEntity deviceMonConfig) {
		return getSqlSession().selectList("deviceMonConf.setDeviceMonConfigExactly", deviceMonConfig);
	}
	@Override
	public Integer selectDataTypeCount(DeviceMonConfigEntity deviceMonConfig) {
		return getSqlSession().selectOne("deviceMonConf.selectDataTypeCount", deviceMonConfig);
	}
	@Override
	public List<DeviceMonitorEntity> selectForMonitor(DeviceMonConfigEntity deviceMonConfig) {
		return getSqlSession().selectList("deviceMonConf.selectForMonitor", deviceMonConfig);
	}
	@Override
	public int updateDeviceMonLibrary(Map<String, Object> param) {
		return this.sqlSessionTemplate.update("deviceMonConf.updateDeviceMonLibrary",param);
	}
	@Override
	public List<DeviceMonConfigEntity> selectDeviceMonModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceMonConf.selectDeviceMonModalByLibraryIdx",params);
	}
	@Override
	public List<DeviceMonTempEntity> selectDeviceMonTempModalByLibraryIdx(
			Map<String, Object> params) {
		return this.sqlSessionTemplate.selectList("deviceMonConf.selectDeviceMonTempModalByLibraryIdx",params);
	}
	@Override
	public int insertDeviceMonTempWithIdx(DeviceMonTempEntity ddJson) {
		return sqlSessionTemplate.insert("deviceMonConf.insertDeviceMonTempWithIdx", ddJson);
	}

}
