package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MetadataDeviceDbDao;
import com.ssitcloud.entity.MetadataDeviceDbAndTableInfoEntity;
import com.ssitcloud.entity.MetadataDeviceDbEntity;
import com.ssitcloud.service.MetadataDeviceDbService;

@Service
public class MetadataDeviceDbServiceImpl implements MetadataDeviceDbService {

	@Resource
	MetadataDeviceDbDao metadataDeviceDbDao;
	
	@Override
	public List<MetadataDeviceDbEntity> selectMetadataDeviceDb(
			MetadataDeviceDbEntity metadataDeviceDbEntity) {
		return metadataDeviceDbDao.select(metadataDeviceDbEntity);
	}
	
	@Override
	public List<MetadataDeviceDbAndTableInfoEntity> SelectMetadataDeviceDbAndTableInfo(
			MetadataDeviceDbEntity metadataDeviceDbEntity) {
		return metadataDeviceDbDao.selectDbAndTableInfo(metadataDeviceDbEntity);
	}

}
