package com.ssitcloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.AppVersionInfoDao;
import com.ssitcloud.service.AppVersionInfoService;

@Service
public class AppVersionInfoServiceImpl implements AppVersionInfoService {
	
	@Resource
	private AppVersionInfoDao appVersionInfoDao;

	@Override
	public List<Map<String, Object>> getAllUsefulAppVersionInfo() {
		return appVersionInfoDao.getAllUsefulAppVersionInfo();
	}
	
	

}
