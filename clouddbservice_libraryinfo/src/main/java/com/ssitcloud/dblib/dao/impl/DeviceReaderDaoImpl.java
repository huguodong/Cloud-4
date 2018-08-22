package com.ssitcloud.dblib.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.dao.DeviceReaderDao;
import com.ssitcloud.dblib.entity.DeviceReaderEntity;
import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;

@Repository
public class DeviceReaderDaoImpl extends CommonDaoImpl implements DeviceReaderDao {

	@Override
	public int saveDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity) {
		return this.sqlSessionTemplate.insert("devicereader.saveDeviceReaderInfo",deviceReaderEntity);
	}
	@Override
	public int updateDeviceReaderInfo(DeviceReaderEntity deviceReaderEntity) {
		return this.sqlSessionTemplate.update("devicereader.updateDeviceReaderInfo",deviceReaderEntity);
	}

	
	@Override
	public int countReaderByLibIdxAndCardno(DeviceReaderEntity deviceReaderEntity) {
		return this.sqlSessionTemplate.selectOne("devicereader.countReaderByLibIdxAndCardno",deviceReaderEntity);
	}
	
	
}
