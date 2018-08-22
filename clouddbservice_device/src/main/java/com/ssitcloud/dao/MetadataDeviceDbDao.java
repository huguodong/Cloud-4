package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.common.dao.CommonDao;
import com.ssitcloud.entity.MetadataDeviceDbAndTableInfoEntity;
import com.ssitcloud.entity.MetadataDeviceDbEntity;


public interface MetadataDeviceDbDao extends CommonDao {
	

	public abstract List<MetadataDeviceDbEntity> select(
			MetadataDeviceDbEntity metadataDeviceDbEntity);

	public abstract List<MetadataDeviceDbAndTableInfoEntity> selectDbAndTableInfo(
			MetadataDeviceDbEntity metadataDeviceDbEntity);
}
