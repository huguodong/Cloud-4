package com.ssitcloud.business.persettingmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.persettingmgmt.service.PersonalSettingService;
import com.ssitcloud.common.entity.ResultEntity;

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
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URl_insertPersonalSetting);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity updatePersonalSetting(String req) {
		
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URl_updatePersonalSetting);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity deletePersonalSetting(String req) {
		
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URl_deletePersonalSetting);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity selectPersonalSetting(String req) {
		
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URl_selectPersonalSetting);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
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
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_selectStatisticsTemplates);
		Map<String,String> map=new HashMap<>();
		map.put("json",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	

}
