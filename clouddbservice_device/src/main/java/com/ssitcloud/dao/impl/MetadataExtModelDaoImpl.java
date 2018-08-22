package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetadataExtModelDao;
import com.ssitcloud.entity.DeviceExtConfigEntity;
import com.ssitcloud.entity.MetadataExtModelEntity;
import com.ssitcloud.system.entity.MetadataExtModelRemoteEntity;

@Repository
public class MetadataExtModelDaoImpl extends CommonDaoImpl implements MetadataExtModelDao{
	@Override
	public List<MetadataExtModelEntity> selectList(DeviceExtConfigEntity deviceExtConfig){
		return getSqlSession().selectList("metadataExtModel.selectListByDeviceExtConfig", deviceExtConfig);
	}
	
	@Override
	public List<MetadataExtModelRemoteEntity> selectListByDeviceExtConfigDown(DeviceExtConfigEntity deviceExtConfig){
		return getSqlSession().selectList("metadataExtModel.selectListByDeviceExtConfigDown", deviceExtConfig);
	}
	
	@Override
	public List<MetadataExtModelEntity> select(MetadataExtModelEntity metadataExtModelEntity){
		return getSqlSession().selectList("metadataExtModel.select", metadataExtModelEntity);
	}

	@Override
	public MetadataExtModelEntity selOneByIdx(
			MetadataExtModelEntity metadataExtModelEntity) {
		return getSqlSession().selectOne("metadataExtModel.selOneByIdx", metadataExtModelEntity);
	}

	@Override
	public int updateByMap(Map<String, Object> m) {
		return getSqlSession().update("metadataExtModel.updateByMap", m);
	}

	@Override
	public int insertMapWithIdx(Map<String, Object> m) {
		return getSqlSession().insert("metadataExtModel.insertMapWithIdx", m);
	}
}
