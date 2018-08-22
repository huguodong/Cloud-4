package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.RelDeviceGroupDao;
import com.ssitcloud.entity.RelDeviceGroupEntity;
import com.ssitcloud.service.RelDeviceGroupService;
/**
 * 
 * @comment 设备分组关联表Service
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Service
public class RelDeviceGroupServiceImpl implements RelDeviceGroupService {

	@Resource
	RelDeviceGroupDao relDeviceGroupDao;
	
	@Override
	public int addRelDeviceGroup(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return relDeviceGroupDao.insert(relDeviceGroupEntity);
	}

	@Override
	public int updRelDeviceGroup(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return relDeviceGroupDao.update(relDeviceGroupEntity);
	}

	@Override
	public int delRelDeviceGroup(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return relDeviceGroupDao.delete(relDeviceGroupEntity);
	}

	@Override
	public List<RelDeviceGroupEntity> selbyidRelDeviceGroup(
			RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return relDeviceGroupDao.selectByid(relDeviceGroupEntity);
	}

	@Override
	public List<RelDeviceGroupEntity> selallRelDeviceGroup() {
		// TODO Auto-generated method stub
		return relDeviceGroupDao.selectAll();
	}

}
