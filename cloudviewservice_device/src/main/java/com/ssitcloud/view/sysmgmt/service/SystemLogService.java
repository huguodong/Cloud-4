package com.ssitcloud.view.sysmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.common.service.BasicService;
/**
 * 
 * @comment 
 * @date 2016年5月31日
 * @author hwl
 */
public interface SystemLogService extends BasicService{

	/**
	 * 查询系统日志信息
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年5月31日
	 * @author hwl
	 */
	String GetSystemloginfo(String json);
	
	/**
	 * 查询操作员list
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年5月31日
	 * @author hwl
	 */
	String GetOperatorList(String json);
	/**
	 * 获取log_message 操作类型，并且设置下拉框
	 * 
	 * @param req
	 * @return
	 */
	ResultEntity getOperationLogType(String req);
}
