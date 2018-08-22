package com.ssitcloud.dao.impl;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.MetaRunConfigDao;
import com.ssitcloud.entity.DeviceRunConfigEntity;
import com.ssitcloud.entity.MetaRunConfigEntity;
import com.ssitcloud.system.entity.MetaRunConfigRemoteEntity;

@Repository
public class MetaRunConfigDaoImpl extends CommonDaoImpl implements MetaRunConfigDao{
	@Override
	public List<MetaRunConfigRemoteEntity> selectListByDeviceRunConfig(DeviceRunConfigEntity deviceRunConfig){
		return getSqlSession().selectList("metaRunConfig.selectListByDeviceRunConfig", deviceRunConfig);
	}

	@Override
	public MetaRunConfigEntity selMetaRunConfigEntityByConfType(
			MetaRunConfigEntity metaRunConfigEntity) {
		return getSqlSession().selectOne("metaRunConfig.selMetaRunConfigEntityByConfType",metaRunConfigEntity);
	}

	@Override
	public MetaRunConfigEntity selOneByIdx(MetaRunConfigEntity metaRunConfig) {
		return getSqlSession().selectOne("metaRunConfig.selOneByIdx",metaRunConfig);
	}

	@Override
	public int updateByMap(Map<String, Object> m) {
		return getSqlSession().update("metaRunConfig.updateByMap",m);
	}

	@Override
	public int insertMapWithIdx(Map<String, Object> m) {
		return getSqlSession().insert("metaRunConfig.insertMapWithIdx", m);
	}
	
	
}
