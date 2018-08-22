package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MetadataOrderDao;
import com.ssitcloud.entity.MetadataOrderEntity;
import com.ssitcloud.service.MetadataOrderService;
/**
 * 
 * @comment 监控指令元数据表Service
 * 
 * @author hwl
 * @data 2016年4月9日
 */
@Service
public class MetadataOrderServiceImpl implements MetadataOrderService {

	@Resource
	MetadataOrderDao metadataOrderDao;

	@Override
	public int addMetadataOrder(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return metadataOrderDao.insert(metadataOrderEntity);
	}

	@Override
	public int updateMetadataOrder(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		if(metadataOrderEntity == null) return 0;
		return metadataOrderDao.update(metadataOrderEntity);
	}

	@Override
	public int deleteMetadataOrder(MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		if(metadataOrderEntity == null) return 0;
		return metadataOrderDao.delete(metadataOrderEntity);
	}

	@Override
	public List<MetadataOrderEntity> selectMetadataOrder(
			MetadataOrderEntity metadataOrderEntity) {
		// TODO Auto-generated method stub
		return metadataOrderDao.select(metadataOrderEntity);
	}
	
}


