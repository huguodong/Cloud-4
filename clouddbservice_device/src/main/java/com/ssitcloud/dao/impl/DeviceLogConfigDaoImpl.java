package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceLogConfigDao;
import com.ssitcloud.entity.DeviceLogConfigEntity;

/**
 * 
 * @comment 实现设备运行日志配置的增删改查
 * 
 * @author hwl
 * @data 2016年4月11日
 */
@Repository
public class DeviceLogConfigDaoImpl extends CommonDaoImpl implements
		DeviceLogConfigDao {

	@Override
	public int insert(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("devicelogconfig.insert",
				deviceLogConfigEntity);
	}

	@Override
	public int update(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("devicelogconfig.update",
				deviceLogConfigEntity);
	}

	@Override
	public int delete(DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("devicelogconfig.delete", deviceLogConfigEntity);
	}

	@Override
	public List<DeviceLogConfigEntity> select(
			DeviceLogConfigEntity deviceLogConfigEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("devicelogconfig.select",
				deviceLogConfigEntity);
	}

}
