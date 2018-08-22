package com.ssitcloud.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.AppVersionInfoDao;

@Repository
public class AppVersionInfoDaoImpl extends CommonDaoImpl implements AppVersionInfoDao{

	@Override
	public List<Map<String, Object>> getAllUsefulAppVersionInfo() {
		
		return this.sqlSessionTemplate.selectList("appinfo.getAllUsefulAppVersionInfo");
	}
	
	

}
