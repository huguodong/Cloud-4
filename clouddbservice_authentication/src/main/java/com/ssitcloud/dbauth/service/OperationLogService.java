package com.ssitcloud.dbauth.service;

import java.util.List;




import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.SystemLogPageEntity;

/**
 * 
 * <p>2016年4月11日 上午11:10:43
 * @author hjc
 *
 */
public interface OperationLogService {
	/**
	 * 新增操作记录
	 * 
	 * <p>2016年4月11日 下午4:49:47
	 * <p>create by hjc
	 * @param operationLogEntity  操作日志实体类
	 * @return 返回数据库操作结果
	 */
	public abstract int addOperationLog(OperationLogEntity operationLogEntity);
	
	/**
	 * 新增操作员operator_id的操作日志
	 * 
	 * <p>2016年4月11日 下午7:45:12
	 * <p>create by hjc
	 * @param operationLogEntity
	 * @param operator_id
	 * @return
	 */
	public abstract int addOperationLogWithOperatorId(OperationLogEntity operationLogEntity,String operator_id);
	
	/**
	 * 新增操作员operator_idx的操作日志
	 *
	 * <p>2016年5月5日 下午5:06:46 
	 * <p>create by hjc
	 * @param operationLogEntity
	 * @param operator_idx
	 * @return
	 */
	public abstract int addOperationLogWithOperatorIdx(OperationLogEntity operationLogEntity,String operator_idx);
	
	public abstract SystemLogPageEntity getOperationLog(SystemLogPageEntity systemLogPageEntity);
	
	public abstract List<OperatorEntity> getOperatorInfo(OperatorEntity operatorEntity);
	/**
	 * 获取log_message 日志 操作类型
	 * @return
	 */
	public abstract ResultEntity getOperationLogType();
}
