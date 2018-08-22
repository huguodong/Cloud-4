package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.RelServiceGroupDao;
import com.ssitcloud.entity.RelServiceGroupEntity;
import com.ssitcloud.service.RelServiceGroupService;

/**
 * 
 * @comment 业务分组关联表Service
 * 
 * @author hwl
 * @data 2016年4月6日
 */

@Service
public class RelServiceGroupServiceImpl implements RelServiceGroupService {

	@Resource
	RelServiceGroupDao relServiceGroupDao;

	@Override
	public int addRelServiceGroup(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return relServiceGroupDao.insert(relServiceGroupEntity);
	}

	@Override
	public int updRelServiceGroup(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return relServiceGroupDao.update(relServiceGroupEntity);
	}

	@Override
	public int delRelServiceGroup(RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return relServiceGroupDao.delete(relServiceGroupEntity);
	}

	@Override
	public List<RelServiceGroupEntity> selbyidRelServiceGroup(
			RelServiceGroupEntity relServiceGroupEntity) {
		// TODO Auto-generated method stub
		return relServiceGroupDao.selectByidx(relServiceGroupEntity);
	}

	@Override
	public List<RelServiceGroupEntity> selallRelServiceGroup() {
		// TODO Auto-generated method stub
		return relServiceGroupDao.selectAll();
	}

}
