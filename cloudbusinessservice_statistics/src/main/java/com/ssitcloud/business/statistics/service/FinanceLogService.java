package com.ssitcloud.business.statistics.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.FinanceLogEntity;

public interface FinanceLogService {
	public ResultEntity queryAllFinanceLog();
	
	
	public FinanceLogEntity queryEsLastFinancelog(String indexName, String typeName);
	
	/**
	 * 查询mongodb的办证数据
	 * @param mongodbName
	 * @return
	 */
	public ResultEntity queryAllFinancelogFromMongodb(String mongodbName);
	
	/**
	 * 保存数据到es中
	 * @param indexName
	 * @param typeName
	 * @param FinanceLogEntity
	 */
	public void saveFinanceLog(String indexName, String typeName, FinanceLogEntity financeLogEntity);
	
	public void saveFinanceLog(String indexName, String typeName, Map<String, Object> map);
	
	
}
