package com.ssitcloud.business.readermgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.readermgmt.service.ConfigFieldService;
import com.ssitcloud.common.entity.ResultEntity;
@Service
public class ConfigFieldServiceImpl extends BasicServiceImpl implements ConfigFieldService {
	private static final String URL_selectConfigFieldList = "selectConfigFieldList";
	private static final String URL_takeDataSource = "takeReaderDataSource";
	private static final String URL_insertConfigTemplate = "insertConfigTemplate";
	private static final String URL_selectConfigTemplatePage = "selectConfigTemplatePage";
	private static final String URL_selectImportConfig = "selectImportConfig";
	private static final String URL_updateImportTemplate = "updateImportTemplate";
	private static final String URL_deleteImportTemplate = "deleteImportTemplate";
	@Override
	public ResultEntity takeDataSource(String req) {
		return postUrl(URL_takeDataSource, req);
	}
	
	@Override
	public ResultEntity selectConfigFieldList(String req) {
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(URL_selectConfigFieldList);
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
	public ResultEntity insertConfigTemplate(String req) {
		String url = requestURL.getRequestURL(URL_insertConfigTemplate);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	
	@Override
	public ResultEntity selectConfigTemplatePage(String req) {
		return postUrl(URL_selectConfigTemplatePage, req);
	}
	
	@Override
	public ResultEntity queryOneImportConfig(String req) {
		return postUrl(URL_selectImportConfig, req);
	}
	
	@Override
	public ResultEntity updateImportTemplate(String req) {
		String url = requestURL.getRequestURL(URL_updateImportTemplate);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	
	@Override
	public ResultEntity deleteImportTemplate(String req) {
		String url = requestURL.getRequestURL(URL_deleteImportTemplate);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
}
