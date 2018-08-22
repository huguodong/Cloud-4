package com.ssitcloud.business.datasync.service;

import com.ssitcloud.devmgmt.entity.DeviceEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.datasync.entity.UploadcfgSynResultEntity;

public interface DataSyncService {

	DeviceEntity selDevice(String deviceId);
	/**
	 * 设备端上传配置信息
	 * @param req
	 * @return
	 */
	ResultEntityF<UploadcfgSynResultEntity> uploadcfgSyn(String req);
	/**
	 * 设备端请求下载配置信息
	 * @param req
	 * @return
	 */
	ResultEntity downloadCfgSync(String req);
	/**
	 * 设备端请求获取 版本信息
	 * @param req
	 * @return
	 */
	ResultEntity askVersion(String req);
	/**
	 * 设备端上传 状态 信息
	 * @param req
	 * @return
	 */
	ResultEntity transData(String req);

}
