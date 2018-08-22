package com.ssitcloud.business.statistics.service;


import java.util.Map;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.statistics.entity.LoanLogEntity;
import com.ssitcloud.statistics.entity.ReaderCirculationEntity;

public interface LoanLogService {
	public ResultEntity queryAllLoanLog();
	
	public LoanLogEntity queryEsLastLoanlog(String indexName, String typeName);
	
	public ResultEntity queryAllLoanlogFromMongodb(String mongodbName);
	
	public ResultEntity queryLoanLogFromMongodb(String req); 
	
	public void saveLoanLog(String indexName, String typeName, LoanLogEntity loanLogEntity);
	
	public void saveLoanLog(String indexName, String typeName, Map<String, Object> map);
	
	public ResultEntity queryLoadLogFromMondbByPage(String req);
	
	public ResultEntity selectReaderCardByParams(String req);

	public ResultEntity insertReaderCard(String req);
	
	public ResultEntity queryCollegeInfo(String req);
	
	public ReaderCirculationEntity queryReaderEsLastLoanlog(String indexName, String typeName,
			 ReaderCirculationEntity circulationEntity);
	

	
	
	
	
}
