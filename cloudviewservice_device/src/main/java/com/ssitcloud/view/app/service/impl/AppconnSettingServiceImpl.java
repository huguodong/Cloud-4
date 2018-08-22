package com.ssitcloud.view.app.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.app.service.AppconnSettingService;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;


@Service
public class AppconnSettingServiceImpl extends BasicServiceImpl implements AppconnSettingService {
	
	private final String URL_INSERT_APPCONNSETTING="insertAppconnSetting";
	private final String URL_UPDATE_APPCONNSETTING="updateAppconnSetting";
	private final String URL_DELETE_APPCONNSETTING="deleteAppconnSetting";
	private final String URL_QUERY_APPCONNSETTING="selectAppconnSetting";//只返回一个实体
	private final String URL_QUERY_APPCONNSETTING_S="selectAppconnSettingByPage";//返回多个实体
	private final String URL_QUERY_SELECTSYSNAME="selectSysName";
	private final String URL_QUERY_APPCONNDATABYSYSNAME="selectAppconnDataBySysName";
	@Override
	public ResultEntity insertAppconnSetting(String req) {
		return postUrl(URL_INSERT_APPCONNSETTING,req);
	}
	@Override
	public ResultEntity updateAppconnSetting(String req) {
		return postUrl(URL_UPDATE_APPCONNSETTING,req);
	}
	@Override
	public ResultEntity deleteAppconnSetting(String req) {
		return postUrl(URL_DELETE_APPCONNSETTING,req);
	}
	@Override
	public ResultEntity selectAppconnSetting(String req) {
		return postUrl(URL_QUERY_APPCONNSETTING,req);
	}
	@Override
	public ResultEntity selectAppconnSettingByPage(String req) {
		return postUrl(URL_QUERY_APPCONNSETTING_S,req);
	}
	@Override
	public ResultEntity selectSysName() {
		return postUrl(URL_QUERY_SELECTSYSNAME,null);
	}
	@Override
	public ResultEntity selectAppconnDataBySysName(String req) {
		return postUrl(URL_QUERY_APPCONNDATABYSYSNAME,req);
	}

	

}
