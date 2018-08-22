package com.ssitcloud.dbauth.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.dbauth.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbauth.dao.OperationLogDao;
import com.ssitcloud.dbauth.entity.LogMessageEntity;
import com.ssitcloud.dbauth.entity.OperationLogEntity;
import com.ssitcloud.dbauth.entity.OperatorEntity;
import com.ssitcloud.dbauth.entity.page.SystemLogPageEntity;

/**
 * <p>2016年4月11日 上午11:12:21
 * @author hjc
 *
 */
@Repository
public class OperationLogDaoImpl extends CommonDaoImpl implements OperationLogDao{

	@Override
	public int addOperationLog(OperationLogEntity operationLogEntity) {
		return this.sqlSessionTemplate.insert("operationLog.addOperationLog",operationLogEntity);
	}
	@SuppressWarnings("unchecked")
	@Override
	public SystemLogPageEntity getOperationLogs(SystemLogPageEntity systemLogPageEntity) {
		SystemLogPageEntity totaLogPageEntity = this.sqlSessionTemplate.selectOne("operationLog.selectOperationLogs", systemLogPageEntity);
		systemLogPageEntity.setTotal(totaLogPageEntity.getTotal());
		systemLogPageEntity.setDoAount(false);
		systemLogPageEntity.setRows(this.sqlSessionTemplate.selectList("operationLog.selectOperationLogs", systemLogPageEntity));
		return systemLogPageEntity;
	}

	@Override
	public List<OperatorEntity> getOperatorInfo(OperatorEntity operatorEntity) {
		return this.sqlSessionTemplate.selectList("operationLog.selectOperator", operatorEntity);
	}
	@Override
	public List<LogMessageEntity> getOperationLogType() {
		return this.sqlSessionTemplate.selectList("operationLog.getOperationLogType");
	}
	
	
	

}
