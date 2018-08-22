package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.RelOperatorServiceGroupDao;
import com.ssitcloud.entity.RelOperatorServiceGroupEntity;
import com.ssitcloud.service.RelOperatorServiceGroupService;

/**
 * @comment 操作员组和业务组关联表service
 * @author hwl
 * 
 * @data 2016-3-29下午3:40:01
 * 
 */
@Service
public class RelOperatorServiceGroupServiceImpl implements
		RelOperatorServiceGroupService {
	@Resource
	private RelOperatorServiceGroupDao relOperatorServiceGroupDao;

	@Override
	public int addRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorServiceGroupDao.insert(reloperatorserviceGroupentity);
	}

	@Override
	public int updRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorServiceGroupDao.update(reloperatorserviceGroupentity);
	}

	@Override
	public int delRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorServiceGroupDao.delete(reloperatorserviceGroupentity);
	}

	@Override
	public List<RelOperatorServiceGroupEntity> selbyidRelOperatorServiceGroup(
			RelOperatorServiceGroupEntity reloperatorserviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorServiceGroupDao
				.selectByidx(reloperatorserviceGroupentity);
	}

	@Override
	public List<RelOperatorServiceGroupEntity> selallRelOperatorServiceGroup() {
		// TODO Auto-generated method stub
		return relOperatorServiceGroupDao.selectAll();
	}

}
