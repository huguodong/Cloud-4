package com.ssitcloud.datasync.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.service.impl.BasicServiceImpl;
import com.ssitcloud.common.system.BeanFactoryHelper;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.datasync.service.DataSyncCommand;
import com.ssitcloud.datasync.service.TransOperationLogService;

@Service
public class TransOperationLogServiceImpl extends BasicServiceImpl implements TransOperationLogService{

	@Override
	public RespEntity executeTransDataLog(String req) {
		
		DataSyncCommand dataSyncExecute=BeanFactoryHelper.getBean("transData", DataSyncCommand.class);
		RequestEntity requestEntity=JsonUtils.fromJson(req, RequestEntity.class);
		return dataSyncExecute.execute(requestEntity);
	}

	@Override
	public RespEntity executeTransOperationLog(String req) {
		
		DataSyncCommand dataSyncExecute=BeanFactoryHelper.getBean("transOperationLog", DataSyncCommand.class);
		
		RequestEntity requestEntity=JsonUtils.fromJson(req, RequestEntity.class);
		
		
		return dataSyncExecute.execute(requestEntity);
	}
	
}
