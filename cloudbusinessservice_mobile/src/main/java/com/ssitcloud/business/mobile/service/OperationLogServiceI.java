package com.ssitcloud.business.mobile.service;

import com.ssitcloud.business.mobile.operationEntity.AbstractOperationLog;

/**
 * 操作日志接口
 * @author LXP
 * @version 创建时间：2017年3月4日 下午4:10:27
 */
public interface OperationLogServiceI {
	/**
	 * 添加一条日志
	 * @return
	 */
	Boolean addOperationLog(AbstractOperationLog operationLog);
}
