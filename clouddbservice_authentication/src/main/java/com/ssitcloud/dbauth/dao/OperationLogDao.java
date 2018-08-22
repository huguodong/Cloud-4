package com.ssitcloud.dbauth.dao;

import java.util.List;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.common.dao.CommonDao;
import com.ssitcloud.dbauth.entity.LogMessageEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.SystemLogPageEntity;

/**
 * <p>2016年4月11日 上午11:11:53
 * @author hjc
 *
 */
public interface OperationLogDao extends CommonDao {

	/**
	 * 新增操作记录
	 * 
	 * <p>2016年4月11日 下午4:49:47
	 * <p>create by hjc
	 * @param operationLogEntity  操作日志实体类
	 * @return 返回数据库操作结果
	 */
	public abstract int addOperationLog(OperationLogEntity operationLogEntity);
	
	public abstract SystemLogPageEntity getOperationLogs(SystemLogPageEntity systemLogPageEntity);
	
	public abstract List<OperatorEntity> getOperatorInfo(OperatorEntity operatorEntity);
	/**
	 * 获取log_message 日志 操作类型
	 * @return
	 */
	public abstract List<LogMessageEntity> getOperationLogType();
}
