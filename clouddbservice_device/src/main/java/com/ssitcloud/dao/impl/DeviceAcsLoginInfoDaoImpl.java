package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceAcsLoginInfoDao;
import com.ssitcloud.datasync.entity.DeviceAcsModuleEntity;
import com.ssitcloud.datasync.entity.ProtocolInfo;
import com.ssitcloud.entity.DeviceAcsLoginInfoEntity;
import com.ssitcloud.system.entity.DeviceAcsLoginInfoRemoteEntity;

@Repository
public class DeviceAcsLoginInfoDaoImpl extends CommonDaoImpl implements DeviceAcsLoginInfoDao{

	@Override
	public int insertAcsInfo(DeviceAcsLoginInfoEntity infoEntity) {
		return this.sqlSessionTemplate.insert("deviceAcsInfo.insertAcsInfo", infoEntity);
	}
	@Override
	public List<DeviceAcsLoginInfoEntity> queryAcsInfoByDeviceIdx(Integer deviceIdx){
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryAcsInfoByDeviceIdx", deviceIdx);
	}
	@Override
	public int deleteAcsInfoByDeviceIdx(int deviceIdx) {
		return this.sqlSessionTemplate.delete("deviceAcsInfo.deleteAcsInfoByDeviceIdx", deviceIdx);
	}
	@Override
	public int insertAcsInfoBatch(List<DeviceAcsLoginInfoEntity> infoEntitys) {
		return this.sqlSessionTemplate.insert("deviceAcsInfo.insertAcsInfoBatch", infoEntitys);
	}
	@Override
	public List<DeviceAcsLoginInfoRemoteEntity> queryAcsInfoByDeviceId(
			String device_id) {
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryAcsInfoByDeviceId", device_id);
	}
	@Override
	public List<DeviceAcsLoginInfoEntity> loadAcsLogininfoByDeviceIdx(
			Integer deviceIdx) {
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.loadAcsLogininfoByDeviceIdx", deviceIdx);
	}
	@Override
	public List<DeviceAcsLoginInfoEntity> queryAcsInfoByProtocolTplIdx(
			Integer protocol_tpl_idx) {
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryAcsInfoByProtocolTplIdx", protocol_tpl_idx);
	}
	@Override
	public int deleteAcsInfoByDeviceIdxs(Map<String, Object> param) {
		return this.sqlSessionTemplate.delete("deviceAcsInfo.deleteAcsInfoByDeviceIdxs", param);
	}
	
	@Override
	public List<DeviceAcsModuleEntity> queryDeviceAcsModules(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryDeviceAcsModules", param);
	}

	@Override
	public List<ProtocolInfo> queryDeviceAcsDetailModules(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryDeviceAcsDetailModules", param);
	}
	@Override
	public List<DeviceAcsLoginInfoEntity> queryAcsInfoByParams(Map<String, String> params) {
		return this.sqlSessionTemplate.selectList("deviceAcsInfo.queryAcsInfoByParams",params);
	}

}
