package com.ssitcloud.dblib.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.SyncConfigDao;
import com.ssitcloud.dblib.entity.SyncConfigEntity;
import com.ssitcloud.dblib.entity.SyncConfigEntityF;

@Repository
public class SyncConfigDaoImpl extends CommonDaoImpl implements SyncConfigDao{


	@Override
	public List<SyncConfigEntity> SelSyncConfig(SyncConfigEntity syncConfig) {
		return this.sqlSessionTemplate.selectList("syncConfig.selectList", syncConfig);
	}

	@Override
	public int insertCloudDbSyncConfig(SyncConfigEntity syncConfigEntity) {
		return this.sqlSessionTemplate.insert("syncConfig.insertCloudDbSyncConfig", syncConfigEntity);
	}

	@Override
	public List<SyncConfigEntity> selectCloudDbSyncConfig(SyncConfigEntity configEntity) {
		
		return this.sqlSessionTemplate.selectList("syncConfig.selectCloudDbSyncConfig",configEntity);
	}

	@Override
	public int updateCloudDbSyncConfig(SyncConfigEntity configEntity) {
		return this.sqlSessionTemplate.update("syncConfig.updateCloudDbSyncConfig", configEntity);
	}
	
	@Override
	public List<SyncConfigEntityF> SelSyncConfigByLibId(SyncConfigEntity syncConfig) {
		return this.sqlSessionTemplate.selectList("syncConfig.selectListByLibId", syncConfig);
	}


}
