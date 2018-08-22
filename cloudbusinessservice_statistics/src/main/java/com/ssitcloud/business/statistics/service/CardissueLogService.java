package com.ssitcloud.business.statistics.service;

import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.CardissueLogEntity;

public interface CardissueLogService {
	public ResultEntity queryAllCardissueLog();
	
	
	public CardissueLogEntity queryEsLastCardissuelog(String indexName, String typeName);
	
	/**
	 * 查询mongodb的办证数据
	 * @param mongodbName
	 * @return
	 */
	public ResultEntity queryAllCardissuelogFromMongodb(String mongodbName);
	
	/**
	 * 保存数据到es中
	 * @param indexName
	 * @param typeName
	 * @param cardissueLogEntity
	 */
	public void saveCardissueLog(String indexName, String typeName, CardissueLogEntity cardissueLogEntity);
	
	public void saveCardissueLog(String indexName, String typeName, Map<String, Object> map);
	
	
}
