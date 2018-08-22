package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.RelDeviceGroupDao;
import com.ssitcloud.entity.RelDeviceGroupEntity;

/**
 * 
 * @comment 实现设备分组关联表的增删改查。表名： rel_device_group
 * 
 * @author hwl
 * @data 2016年4月6日
 */
@Repository
public class RelDeviceGroupDaoImpl extends CommonDaoImpl implements
		RelDeviceGroupDao {

	@Override
	public int insert(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("reldevicegroup.insert",
				relDeviceGroupEntity);
	}

	@Override
	public int update(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("reldevicegroup.update",
				relDeviceGroupEntity);
	}

	@Override
	public int delete(RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("reldevicegroup.delete",
				relDeviceGroupEntity);
	}

	@Override
	public List<RelDeviceGroupEntity> selectByid(
			RelDeviceGroupEntity relDeviceGroupEntity) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("reldevicegroup.selectByidx",
				relDeviceGroupEntity);
	}

	@Override
	public List<RelDeviceGroupEntity> selectAll() {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("reldevicegroup.selectAll");
	}

	@Override
	public int deleteByDeviceIdx(Integer deviceIdx) {
		return this.sqlSessionTemplate.delete("reldevicegroup.deleteByDeviceIdx",deviceIdx);
	}

	@Override
	public int deleteByDeviceIdxs(Map<String, Object> param) {
		return this.sqlSessionTemplate.delete("reldevicegroup.deleteByDeviceIdxs",param);
	}

	@Override
	public int deleteByLibIdx(RelDeviceGroupEntity relDeviceGroupEntity) {
		return this.sqlSessionTemplate.delete("reldevicegroup.deleteByLibIdx",relDeviceGroupEntity);
	}

	
}
