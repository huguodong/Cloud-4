package com.ssitcloud.dbservice.nodemgmt.dao.impl;


import org.springframework.stereotype.Repository;

import com.ssitcloud.dbservice.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dbservice.nodemgmt.dao.ClearHeartBeatDataDao;

@Repository
public class ClearHeartBeatDataDaoImpl extends CommonDaoImpl implements ClearHeartBeatDataDao {

	@Override
	public void clearHeartBeatData() {
		
		this.sqlSessionTemplate.update("clearHeartBeatData.truncateHeartBeatData");
		
	}

}
