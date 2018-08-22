package com.ssitcloud.view.readermgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.view.common.service.impl.BasicServiceImpl;
import com.ssitcloud.view.common.util.JsonUtils;
import com.ssitcloud.view.readermgmt.service.ConfigFieldService;
@Service
public class ConfigFieldServiceImpl extends BasicServiceImpl implements ConfigFieldService {
	private static final String URL_takeDataSource = "takeReaderDataSource";
	private static final String URL_selectConfigFieldList = "selectConfigFieldList";
	private static final String URL_addConfigTemplate = "addConfigTemplate";
	private static final String URL_selectConfigTemplatePage = "selectConfigTemplatePage";
	private static final String URL_updateImportTemplate = "updateImportTemplate";
	private static final String URL_deleteImportTemplate = "deleteImportTemplate";
	private static final String URL_selectImportConfig = "selectImportConfig";
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
	public ResultEntity addConfigTemplate(String req) {
		return postUrl(URL_addConfigTemplate, req);
	}
	
	@Override
	public ResultEntity selectConfigTemplatePage(String req) {
		return postUrl(URL_selectConfigTemplatePage, req);
	}
	
	@Override
	public ResultEntity updateImportTemplate(String req) {
		return postUrl(URL_updateImportTemplate, req);
	}
	
	@Override
	public ResultEntity deleteImportTemplate(String req) {
		return postUrl(URL_deleteImportTemplate, req);
	}
	
	public ResultEntity selectImportConfig(String req){
		return postUrl(URL_selectImportConfig, req);
	}
}
