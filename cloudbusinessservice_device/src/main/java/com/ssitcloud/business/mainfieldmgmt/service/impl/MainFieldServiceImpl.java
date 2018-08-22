package com.ssitcloud.business.mainfieldmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.mainfieldmgmt.service.MainFieldService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class MainFieldServiceImpl extends BasicServiceImpl implements MainFieldService{

	private static final String URL_insertMainField = "insertMainField";
	private static final String URL_updateMainField = "updateMainField";
	private static final String URL_deleteMainField = "deleteMainField";
	private static final String URL_selectMainField = "selectMainField";
	private static final String URL_selectMainFieldByPage = "selectMainFieldByPage";
	private static final String URL_selectMainFieldList = "selectMainFieldList";
	
	@Override
	public ResultEntity insertMainField(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_insertMainField);
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
	public ResultEntity updateMainField(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_updateMainField);
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
	public ResultEntity deleteMainField(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_deleteMainField);
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
	public ResultEntity selectMainField(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_selectMainField);
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
	public ResultEntity selectMainFieldByPage(String req) {
		
		return postUrl(URL_selectMainFieldByPage,req);
	}

	@Override
	public ResultEntity selectMainFieldList(String req) {
		return postUrl(URL_selectMainFieldList,req);
	}

}
