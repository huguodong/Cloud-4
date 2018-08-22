package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.SpecialDeviceTypeDao;
import com.ssitcloud.devmgmt.entity.SpecialDevicePageEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypeEntity;
import com.ssitcloud.devmgmt.entity.SpecialDeviceTypePageEntity;

@Repository
public class SpecialDeviceTypeDaoImpl extends CommonDaoImpl implements SpecialDeviceTypeDao {
	public List<SpecialDeviceTypeEntity> querySpecialDeviceTypeByParams(Map<String, String> map) {
		return getSqlSession().selectList("specialDeviceType.querySpecialDeviceTypeByParams",map);
		
	}

	@Override
	public SpecialDeviceTypePageEntity querySpecialDeviceTypeByPage(SpecialDeviceTypePageEntity pageEntity) {
		SpecialDeviceTypePageEntity deviceTypePageEntity = getSqlSession().selectOne("specialDeviceType.querySpecialDeviceTypeByPage",pageEntity);
		pageEntity.setTotal(deviceTypePageEntity == null?0:deviceTypePageEntity.getTotal());
		pageEntity.setDoAount(false);
		List<SpecialDeviceTypePageEntity> deviceEntities = getSqlSession().selectList("specialDeviceType.querySpecialDeviceTypeByPage",pageEntity);
		pageEntity.setRows(deviceEntities);
		return pageEntity;
	}

	public int addSpecialDeviceType(SpecialDeviceTypeEntity deviceTypeEntity) {
		return getSqlSession().insert("specialDeviceType.addSpecialDeviceType", deviceTypeEntity);
	}

	@Override
	public int deleteSpecialDeviceType(Map<String, String> map) {
		return getSqlSession().delete("specialDeviceType.deleteSpecialDeviceType", map);
	}

	@Override
	public int editSpecialDeviceType(SpecialDeviceTypeEntity deviceTypeEntity) {
		return getSqlSession().update("specialDeviceType.editSpecialDeviceType",deviceTypeEntity);
	}
	
	
	
}
