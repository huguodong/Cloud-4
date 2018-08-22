package com.ssitcloud.businessauth.service;

import com.ssitcloud.businessauth.common.service.BasicService;
import com.ssitcloud.common.entity.ResultEntity;

/**
 * 
 * @comment 
 * @date 2016年5月31日
 * @author hwl
 */
public interface SystemLogService extends BasicService {

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
	 * 添加操作日志
	 * @comment
	 * @param json
	 * @return
	 * @data 2016年5月31日`
	 * @author hwl
	 */
	public String addOperationLog(String json);

	ResultEntity getOperationLogType(String req);
}
