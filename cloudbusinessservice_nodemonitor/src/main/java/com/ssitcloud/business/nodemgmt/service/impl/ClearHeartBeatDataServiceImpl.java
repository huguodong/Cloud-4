package com.ssitcloud.business.nodemgmt.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.nodemgmt.service.ClearHeartBeatDataService;
import com.ssitcloud.dbservice.nodemgmt.dao.ClearHeartBeatDataDao;

@Service
public class ClearHeartBeatDataServiceImpl extends BasicServiceImpl implements ClearHeartBeatDataService{
	
	@Autowired
	private ClearHeartBeatDataDao beatDataDao;
	/**清理心跳数据*/
	public void clearHeartBeatData() {
		beatDataDao.clearHeartBeatData();
	}
	
	
	
}
