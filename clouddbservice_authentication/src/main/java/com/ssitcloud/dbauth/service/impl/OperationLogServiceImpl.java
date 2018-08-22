package com.ssitcloud.dbauth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.dao.OperationLogDao;
import com.ssitcloud.dbauth.dao.OperatorDao;
import com.ssitcloud.dbauth.entity.LogMessageEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.SystemLogPageEntity;
import com.ssitcloud.dbauth.service.OperationLogService;

/**
 * <p>2016年4月11日 上午11:11:23
 * @author hjc
 *
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {
	@Resource
	private OperationLogDao operationLogDao;
	
	@Resource
	private OperatorDao operatorDao;

	@Override
	public int addOperationLog(OperationLogEntity operationLogEntity) {
		return operationLogDao.addOperationLog(operationLogEntity);
	}

	@Override
	public int addOperationLogWithOperatorId(
			OperationLogEntity operationLogEntity, String operator_id) {
		//通过operator_id查询操作员的信息
		OperatorEntity o = new OperatorEntity();
		o.setOperator_id(operator_id);
		o = operatorDao.selOperatorByOperIdOrIdx(o);
		if (o!=null) {
			operationLogEntity.setOperator_idx(o.getOperator_idx());
			return operationLogDao.addOperationLog(operationLogEntity);
		}else {
			return -1;
		}
	}
	
	@Override
	public int addOperationLogWithOperatorIdx(OperationLogEntity operationLogEntity, String operator_idx) {
		operationLogEntity.setOperator_idx(Integer.valueOf(operator_idx));
		return operationLogDao.addOperationLog(operationLogEntity);
	}

	/**
	 *  NOTE:与log_code首位数对应
	 * 	<option value="5">设备管理</option>
		<option value="1">注册操作</option>
		<option value="2">监控管理</option>
		<option value="3">用户管理</option>
		<option value="4">图书馆管理</option>
		<option value="6">系统管理</option>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SystemLogPageEntity getOperationLog(SystemLogPageEntity systemLogPageEntity) {
		List<Map<String, Object>> libinfo = new ArrayList<>();
		SystemLogPageEntity sLogPageEntity = operationLogDao.getOperationLogs(systemLogPageEntity);
		List<SystemLogPageEntity> list = sLogPageEntity.getRows();
		for (SystemLogPageEntity logPageEntity : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("operation_log_idx", logPageEntity.getOpreation_log_idx());
			map.put("operation_time", logPageEntity.getOperation_time().toString());
			map.put("operator_idx", logPageEntity.getOperator_idx());
			map.put("client_ip", logPageEntity.getClient_ip());
			map.put("client_port", logPageEntity.getClient_port());
			map.put("operation_cmd", logPageEntity.getOperation_cmd());
			map.put("operation_result", logPageEntity.getOperation_result());
			map.put("operation", logPageEntity.getOperation());
			map.put("operator_id", logPageEntity.getOperator_id());
			map.put("operator_name", logPageEntity.getOperator_name());
			map.put("error_code", logPageEntity.getError_code());
			map.put("log_desc", logPageEntity.getLog_desc());
			
			libinfo.add(map);
		}
		sLogPageEntity.setRows(libinfo);
		return sLogPageEntity;
	}

	@Override
	public List<OperatorEntity> getOperatorInfo(OperatorEntity operatorEntity) {
		return operationLogDao.getOperatorInfo(operatorEntity);
	}

	@Override
	public ResultEntity getOperationLogType() {
		ResultEntity result=new ResultEntity();
		List<LogMessageEntity> LogMessages=operationLogDao.getOperationLogType();
		result.setState(true);
		result.setResult(LogMessages);
		return result;
	}
	
	
	
}
