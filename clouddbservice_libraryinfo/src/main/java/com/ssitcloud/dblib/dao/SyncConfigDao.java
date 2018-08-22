package com.ssitcloud.dblib.dao;

import java.util.List;

import com.ssitcloud.dblib.entity.SyncConfigEntity;
import com.ssitcloud.dblib.entity.SyncConfigEntityF;

public interface SyncConfigDao {
	public List<SyncConfigEntity> SelSyncConfig(SyncConfigEntity syncConfig);
	
	public List<SyncConfigEntityF> SelSyncConfigByLibId(SyncConfigEntity syncConfig);
	
	public int insertCloudDbSyncConfig(SyncConfigEntity syncConfigEntity);
	public List<SyncConfigEntity>selectCloudDbSyncConfig(SyncConfigEntity configEntity);
	public int updateCloudDbSyncConfig(SyncConfigEntity configEntity);
}
