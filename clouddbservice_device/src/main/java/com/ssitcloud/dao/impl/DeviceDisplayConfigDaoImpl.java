package com.ssitcloud.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.DeviceDisplayConfigDao;
import com.ssitcloud.entity.DeviceDisplayConfig;
import com.ssitcloud.entity.MetadataDeviceTypeEntity;
import com.ssitcloud.entity.page.DeviceDisplayConfigPage;

/**
 * 
 * @comment 设备风格显示。表名：devicedisplay
 * 
 */
@Repository
public class DeviceDisplayConfigDaoImpl extends CommonDaoImpl implements DeviceDisplayConfigDao {

	@Override
	public DeviceDisplayConfigPage queryAll(DeviceDisplayConfigPage entity) {
		DeviceDisplayConfigPage total = this.sqlSessionTemplate.selectOne("devicedisplay.queryAll", entity);
		entity.setTotal(total.getTotal());
		entity.setDoAount(false);
		entity.setRows(this.sqlSessionTemplate.selectList("devicedisplay.queryAll", entity));
		return entity;
	}

	@Override
	public int delete(List<Integer> display_type_idxs) {
		return this.sqlSessionTemplate.delete("devicedisplay.delete", display_type_idxs);
	}

	@Override
	public int update(DeviceDisplayConfig entity) {
		return this.sqlSessionTemplate.update("devicedisplay.update", entity);
	}

	@Override
	public DeviceDisplayConfig insert(DeviceDisplayConfig entity) {
		int count = this.sqlSessionTemplate.insert("devicedisplay.insert", entity);
		if(count>0){
			return entity;
		}
		return null;
	}

	@Override
	public DeviceDisplayConfig findById(int display_type_idx) {
		return this.sqlSessionTemplate.selectOne("devicedisplay.findById", display_type_idx);
	}

	@Override
	public DeviceDisplayConfig findByTypeId(int device_type_idx) {
		return this.sqlSessionTemplate.selectOne("devicedisplay.findByTypeId", device_type_idx);
	}
	
	@Override
	public List<MetadataDeviceTypeEntity> getDeviceTypes(String req) {
		return this.sqlSessionTemplate.selectList("devicedisplay.getDeviceTypes");
	}

}
