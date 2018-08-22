package com.ssitcloud.view.statistics.persettingmgmt.service.impl;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.view.statistics.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.statistics.persettingmgmt.service.PersonalSettingService;

@Service
public class PersonalSettingServiceImpl extends BasicServiceImpl implements PersonalSettingService{
	
	private static final String URl_insertPersonalSetting = "insertPersonalSetting";
	private static final String URl_updatePersonalSetting = "updatePersonalSetting";
	private static final String URl_deletePersonalSetting = "deletePersonalSetting";
	private static final String URl_selectPersonalSetting = "selectPersonalSetting";
	private static final String URl_selectPersonalSettingByPage = "selectPersonalSettingByPage";
	private static final String URL_selOperatorByOperIdOrIdx = "selOperatorByOperIdOrIdx";
	private static final String URL_selectStatisticsTemplates = "selectStatisticsTemplates";

	@Override
	public ResultEntity insertPersonalSetting(String req) {
		return postUrl(URl_insertPersonalSetting,req);
	}

	@Override
	public ResultEntity updatePersonalSetting(String req) {
		return postUrl(URl_updatePersonalSetting,req);
	}

	@Override
	public ResultEntity deletePersonalSetting(String req) {
		return postUrl(URl_deletePersonalSetting,req);
	}

	@Override
	public ResultEntity selectPersonalSetting(String req) {
		return postUrl(URl_selectPersonalSetting,req);
	}

	@Override
	public ResultEntity selOperatorByOperIdOrIdx(String req) {
		return postUrl(URL_selOperatorByOperIdOrIdx,req);
	}

	@Override
	public ResultEntity selectPersonalSettingByPage(String req) {
		return postUrl(URl_selectPersonalSettingByPage,req);
	}
	
	@Override
	public ResultEntity selectStatisticsTemplates(String req) {
		return postUrl(URL_selectStatisticsTemplates,req);
	}

}
