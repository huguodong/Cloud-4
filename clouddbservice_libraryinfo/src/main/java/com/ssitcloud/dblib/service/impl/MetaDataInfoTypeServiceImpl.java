package com.ssitcloud.dblib.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ssitcloud.dblib.dao.MetaDataInfoTypeDao;
import com.ssitcloud.dblib.entity.MetaDataInfoTypeEntity;
import com.ssitcloud.dblib.service.MetaDataInfoTypeService;

@Service
public class MetaDataInfoTypeServiceImpl implements MetaDataInfoTypeService {
	@Resource
	private MetaDataInfoTypeDao metaDataInfoTypeDao;

	@Override
	public MetaDataInfoTypeEntity queryOneMetaDataInfoType(
			MetaDataInfoTypeEntity metaDataInfoTypeEntity) {
		return metaDataInfoTypeDao.queryOneMetaDataInfoType(metaDataInfoTypeEntity);
	}
	
	@Override
	public List<MetaDataInfoTypeEntity> queryMetaDataInfoTypes(
			MetaDataInfoTypeEntity metaDataInfoTypeEntity) {
		return metaDataInfoTypeDao.queryMetaDataInfoTypes(metaDataInfoTypeEntity);
	}

	

}
