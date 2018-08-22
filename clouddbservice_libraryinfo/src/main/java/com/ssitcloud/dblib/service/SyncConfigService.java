package com.ssitcloud.dblib.service;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.entity.SyncConfigEntity;

public interface SyncConfigService {
	
	public List<SyncConfigEntity> SelSyncConfig();
	public ResultEntity addCloudDbSyncConfig(String req);
	public ResultEntity selectCloudDbSyncConfig(String req);
	ResultEntity downloadLibraryInfoCfgSync(String req);
}
