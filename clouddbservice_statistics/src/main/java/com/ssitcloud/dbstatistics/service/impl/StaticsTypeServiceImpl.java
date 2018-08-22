package com.ssitcloud.dbstatistics.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dbstatistics.dao.StaticsTypeDao;
import com.ssitcloud.dbstatistics.entity.StaticsTypeEntity;
import com.ssitcloud.dbstatistics.service.StaticsTypeService;
@Service
public class StaticsTypeServiceImpl implements StaticsTypeService {
	
	@Resource
	private StaticsTypeDao staticsTypeDao;

	@Override
	public int insertStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return staticsTypeDao.insertStaticsType(staticsTypeEntity);
	}

	@Override
	public int deleteStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return staticsTypeDao.deleteStaticsType(staticsTypeEntity);
	}

	@Override
	public int updateStaticsType(StaticsTypeEntity staticsTypeEntity) {
		return staticsTypeDao.updateStaticsType(staticsTypeEntity);
	}

	@Override
	public StaticsTypeEntity queryStaticsType(
			StaticsTypeEntity staticsTypeEntity) {
		return staticsTypeDao.queryStaticsType(staticsTypeEntity);
	}

	@Override
	public List<StaticsTypeEntity> queryStaticsTypeList(
			StaticsTypeEntity staticsTypeEntity) {
		return staticsTypeDao.queryStaticsTypeList(staticsTypeEntity);
	}

}
