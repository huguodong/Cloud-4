package com.ssitcloud.dblib.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dblib.common.utils.ExceptionHelper;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.common.utils.MBeanUtil;
import com.ssitcloud.dblib.dao.SyncConfigDao;
import com.ssitcloud.dblib.entity.DownloadCfgSyncEntity;
import com.ssitcloud.dblib.entity.DownloadLibraryInfoEntity;
import com.ssitcloud.dblib.entity.SyncConfigEntity;
import com.ssitcloud.dblib.entity.SyncConfigEntityF;
import com.ssitcloud.dblib.service.SyncConfigService;

@Service
public class SyncConfigServiceImpl implements SyncConfigService{

	@Resource
	private SyncConfigDao syncConfigDao;
	
	@Override
	public List<SyncConfigEntity> SelSyncConfig() {
		return 	syncConfigDao.SelSyncConfig(new SyncConfigEntity());
	}
	
	@Override
	public ResultEntity downloadLibraryInfoCfgSync(String req) {
		ResultEntity result=new ResultEntity();
		try {
			DownloadCfgSyncEntity downcfgSync=JsonUtils.fromJson(req, DownloadCfgSyncEntity.class);
			String lib_id = downcfgSync.getLib_id();
			String library_idx=downcfgSync.getLibrary_id();
			String device_id=downcfgSync.getDevice_id();
			String tableName=downcfgSync.getTable();
			SyncConfigEntity syncConfig = new SyncConfigEntity();
			syncConfig.setLib_id(lib_id);
			syncConfig.setDevice_id(device_id);
			List<SyncConfigEntityF> list = syncConfigDao.SelSyncConfigByLibId(syncConfig);
			if(list!=null&&list.size()>0){
				result.setResult(MBeanUtil.makeReturnResultEntityFromList(list,tableName));
				result.setState(true);
			}
		} catch (Exception e) {
			ExceptionHelper.afterException(result, Thread.currentThread().getStackTrace()[0].getMethodName(), e);
		}
		return result;
	}

	@Override
	public ResultEntity addCloudDbSyncConfig(String req) {
		if(req != null && req.length() > 0){
			SyncConfigEntity params = new SyncConfigEntity();
			List<SyncConfigEntity> syncConfigEntities = JsonUtils.fromJson(req, new TypeReference<List<SyncConfigEntity>>() {});
			for(SyncConfigEntity configEntity : syncConfigEntities){
				params.setDb_name(configEntity.getDb_name());
				params.setLib_id(configEntity.getLib_id());
				params.setTable_name(configEntity.getTable_name());
				params.setDevice_id(configEntity.getDevice_id());
				List<SyncConfigEntity> entities = syncConfigDao.selectCloudDbSyncConfig(params);
				if(entities != null && !entities.isEmpty()){
					syncConfigDao.updateCloudDbSyncConfig(configEntity);
				}else{
					syncConfigDao.insertCloudDbSyncConfig(configEntity);
				}
			}
		
		}
		return new ResultEntity(true,"");
	}

	@Override
	public ResultEntity selectCloudDbSyncConfig(String req) {
		SyncConfigEntity syncConfigEntity = new SyncConfigEntity();
		ResultEntity entity = new ResultEntity();
		if(req != null && req.length() > 0){
			syncConfigEntity = JsonUtils.fromJson(req, SyncConfigEntity.class);
		}
		List<SyncConfigEntity> entities = new ArrayList<>();
		if(syncConfigEntity.getDb_name() != null && syncConfigEntity.getDb_name().length() > 0){
			String[] db_names = syncConfigEntity.getDb_name().split(",");
			for(String db_name : db_names){
				syncConfigEntity.setDb_name(db_name);
				List<SyncConfigEntity> configEntities = syncConfigDao.selectCloudDbSyncConfig(syncConfigEntity);
				if(configEntities != null && !configEntities.isEmpty()){
					entities.addAll(configEntities);
				}
			}
		}else{
			List<SyncConfigEntity> configEntities = syncConfigDao.selectCloudDbSyncConfig(syncConfigEntity);
			entities.addAll(configEntities);
		}
		
		
		entity.setState(true);
		entity.setResult(entities);
		
		return entity;
	}
	
	
	

}
