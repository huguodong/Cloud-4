package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataDeviceDbDao;
import com.ssitcloud.entity.MetadataDeviceDbAndTableInfoEntity;
import com.ssitcloud.entity.MetadataDeviceDbEntity;

@Repository
public class MetadataDeviceDbDaoImpl extends CommonDaoImpl implements MetadataDeviceDbDao {

	@Override
	public List<MetadataDeviceDbEntity> select(MetadataDeviceDbEntity metadataDeviceDbEntity) {
		return this.sqlSessionTemplate.selectList("metadevicedb.select", metadataDeviceDbEntity);
	}
	
	@Override
	public List<MetadataDeviceDbAndTableInfoEntity> selectDbAndTableInfo(MetadataDeviceDbEntity metadataDeviceDbEntity) {
		return this.sqlSessionTemplate.selectList("metadevicedb.selectDbAndTableInfo", metadataDeviceDbEntity);
	}
}
