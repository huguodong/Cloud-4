package com.ssitcloud.view.devmgmt.service.impl;

import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.HttpClientUtil;
import com.ssitcloud.view.devmgmt.service.DBSyncService;
/**
 * 
 * @comment 
 * @date 2016年6月1日
 * @author hwl
 */
@Service
public class DBSyncServiceImpl extends BasicServiceImpl implements DBSyncService {

	protected static final String URL_SelectDBSynctemp="SelDeviceDbsyncTemp";
	protected static final String URL_AddDBSynctemp="AddNewDeviceDbsyncTemp";
	protected static final String URL_UpdDBSynctemp="UpdDeviceDbsyncTemp";
	protected static final String URL_DelDBSynctemp="DelDeviceDbsyncTemp";
	
	@Override
	public String selDBsyncConfig(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_SelectDBSynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String addDBsyncConfig(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_AddDBSynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String updDBsyncConfig(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_UpdDBSynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}

	@Override
	public String delDBsyncConfig(Map<String, String> map) {
		String reqURL=requestURL.getRequestURL(URL_DelDBSynctemp);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		return result;
	}
	
}
