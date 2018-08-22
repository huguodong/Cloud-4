package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MetadataLogicObjDao;
import com.ssitcloud.entity.MetadataLogicObjEntity;
import com.ssitcloud.service.MetadataLogicObjService;
/**
 * 
 * @comment 逻辑对象元数据表Controller
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Service
public class MetadataLogicObjServiceImpl implements MetadataLogicObjService {

	@Resource
	MetadataLogicObjDao metadataLogicObjDao;
	@Override
	public int addMetadataLogicObj(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return metadataLogicObjDao.insert(metadataLogicObjEntity);
	}

	@Override
	public int deleteMetadataLogicObj(MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return metadataLogicObjDao.delete(metadataLogicObjEntity);
	}

	@Override
	public int updateMetadataLogicObj(
			MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return metadataLogicObjDao.update(metadataLogicObjEntity);
	}

	@Override
	public List<MetadataLogicObjEntity> selectMetadataLogicObj(
			MetadataLogicObjEntity metadataLogicObjEntity) {
		// TODO Auto-generated method stub
		return metadataLogicObjDao.select(metadataLogicObjEntity);
	}

}
