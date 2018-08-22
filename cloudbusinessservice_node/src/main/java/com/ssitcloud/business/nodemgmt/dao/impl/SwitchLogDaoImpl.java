package com.ssitcloud.business.nodemgmt.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.business.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.business.nodemgmt.dao.SwitchLogDao;
import com.ssitcloud.node.entity.SwitchLogEntity;

@Repository
public class SwitchLogDaoImpl extends CommonDaoImpl implements SwitchLogDao {

	@Override
	public SwitchLogEntity addLog(SwitchLogEntity log) {
		int count = this.sqlSessionTemplate.insert("log.addLog", log);
		if (count > 0)
			return log;
		return null;
	}

}
