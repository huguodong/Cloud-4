package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.RelOperatorDeviceGroupDao;
import com.ssitcloud.entity.RelOperatorDeviceGroupEntity;
import com.ssitcloud.service.RelOperatorDeviceGroupService;

/**
 * 
 * @comment 操作员组和设备组关联表Service
 * 
 * @author hwl
 * @data 2016年4月6日
 */

@Service
public class RelOperatorDeviceGroupServiceImpl implements
		RelOperatorDeviceGroupService {

	@Resource
	RelOperatorDeviceGroupDao relOperatorDeviceGroupDao;

	@Override
	public int addRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorDeviceGroupDao.insert(reloperatordeviceGroupentity);
	}

	@Override
	public int updRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorDeviceGroupDao.update(reloperatordeviceGroupentity);
	}

	@Override
	public int delRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorDeviceGroupDao.delete(reloperatordeviceGroupentity);
	}

	@Override
	public List<RelOperatorDeviceGroupEntity> selbyidRelOperatorDeviceGroup(
			RelOperatorDeviceGroupEntity reloperatordeviceGroupentity) {
		// TODO Auto-generated method stub
		return relOperatorDeviceGroupDao
				.selectByidx(reloperatordeviceGroupentity);
	}

	@Override
	public List<RelOperatorDeviceGroupEntity> selallRelOperatorDeviceGroup() {
		// TODO Auto-generated method stub
		return relOperatorDeviceGroupDao.selectAll();
	}

}
