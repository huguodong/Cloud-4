package com.ssitcloud.service;

import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.entity.UploadcfgSynEntity;
import com.ssitcloud.entity.UploadcfgSynResultEntity;

/**
 * 
 * 上传配置，包括 表有
 * device_ext_config、device_run_config
 * @author lbh
 *
 */
public interface UploadCfgSyncService {
	/**
	 * 根据不同的实现，对应不同的上传配置信息
	 * @param downcfgSync
	 * @return
	 */
	ResultEntityF<UploadcfgSynResultEntity> execute(UploadcfgSynEntity uploadcfgSyn);
	
}
