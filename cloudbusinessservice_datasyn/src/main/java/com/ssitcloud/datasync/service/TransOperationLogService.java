package com.ssitcloud.datasync.service;

import com.ssitcloud.common.entity.RespEntity;

public interface TransOperationLogService {

	/**
	 * 处理设备端操作日志：transData
	 * @param req
	 * @return
	 */
	RespEntity executeTransDataLog(String req);
	/**
	 * 处理设备端操作日志：transOperationLog
	 * @param req
	 * @return
	 */
	RespEntity executeTransOperationLog(String req);

}
