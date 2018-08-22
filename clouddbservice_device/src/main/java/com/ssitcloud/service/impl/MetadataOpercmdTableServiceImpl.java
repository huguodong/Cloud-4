package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.MetadataOpercmdTableDao;
import com.ssitcloud.entity.MetadataOpercmdTableEntity;
import com.ssitcloud.redisutils.JedisUtils;
import com.ssitcloud.redisutils.RedisConstant;
import com.ssitcloud.service.MetadataOpercmdTableService;

@Service
public class MetadataOpercmdTableServiceImpl implements
		MetadataOpercmdTableService {
	
	@Resource
	private MetadataOpercmdTableDao metadataOpercmdTableDao;

	@Override
	public List<MetadataOpercmdTableEntity> queryAllOpercmdTable() {
		return metadataOpercmdTableDao.queryAllOpercmdTable();
	}
	
	/**
	 * 加载opercmd_table中的数据到redis
	 */
	public void loadCMDTableToRedis(){
		
		List<MetadataOpercmdTableEntity> entities = queryAllOpercmdTable();
		if(entities != null){
			for(MetadataOpercmdTableEntity entity : entities){
				JedisUtils.getInstance().set(RedisConstant.OPERCMD_TABLE+entity.getOpercmd_id(),entity.getMongodb_table());
				JedisUtils.getInstance().set("opercmd_type:"+entity.getOpercmd_id(),entity.getElectronic_type());
			}
		}
		
	}

	
	
}
