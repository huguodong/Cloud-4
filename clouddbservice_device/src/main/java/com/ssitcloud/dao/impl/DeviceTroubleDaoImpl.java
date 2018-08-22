package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceTroubleDao;
import com.ssitcloud.entity.DeviceTroubleEntity;
import com.ssitcloud.entity.page.DeviceTroublePageEntity;

@Repository
public class DeviceTroubleDaoImpl extends CommonDaoImpl implements
		DeviceTroubleDao {

	@Override
	public int insertDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity) {
		return this.sqlSessionTemplate.insert(
				"device_trouble.insertDeviceTrouble", deviceTroubleEntity);
	}

	@Override
	public int updateDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity) {
		return this.sqlSessionTemplate.update(
				"device_trouble.updateDeviceTrouble", deviceTroubleEntity);
	}

	@Override
	public int deleteDeviceTrouble(DeviceTroubleEntity deviceTroubleEntity) {
		return this.sqlSessionTemplate.delete(
				"device_trouble.deleteDeviceTrouble", deviceTroubleEntity);
	}

	@Override
	public DeviceTroubleEntity queryOneDeviceTrouble(
			DeviceTroubleEntity deviceTroubleEntity) {
		return this.select("device_trouble.selectDeviceTrouble",
				deviceTroubleEntity);
	}

	@Override
	public List<DeviceTroubleEntity> queryDeviceTroubles(
			DeviceTroubleEntity deviceTroubleEntity) {
		return this.sqlSessionTemplate.selectList(
				"device_trouble.selectDeviceTroubles", deviceTroubleEntity);
	}

	@Override
	public DeviceTroublePageEntity selectDeviceTroublesByLibIdxs(
			DeviceTroublePageEntity pageEntity) {
		return this.queryDatagridPage(pageEntity, "device_trouble.selectDeviceTroublesByLibIdxs");
	}

	@Override
	public int updateDeviceTroubles(Map map) {
		return this.sqlSessionTemplate.update("device_trouble.updateDeviceTroubles", map);
	}

}
