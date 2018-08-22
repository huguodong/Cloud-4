package com.ssitcloud.business.statisticsmgmt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.service.impl.BasicServiceImpl;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.statisticsmgmt.service.StatisticsTemplateService;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class StatisticsTemplateServiceImpl extends BasicServiceImpl implements
		StatisticsTemplateService {
	private static final String URL_TAKEDATASOURCE = "takeDataSource";
	private static final String URL_BOOKLOCATION = "selectBookLocations";
	private static final String URL_BOOKCIRTYPE = "selectBookCirtypes";
	private static final String URL_BOOKMEDIATYPE = "selectBookMediatypes";
	private static final String URL_SELECTREADERTYPE = "selectReadertype"; 
	private static final String URL_SELECTMAINFUNS = "selectFunMaindatas"; 
	private static final String URL_SELECTSUBFUNS = "selectFunSubdatas"; 
	
	private static final String URL_INSERTSTATISTICSTEMPLATE = "insertStatisticsTemplate";
	private static final String URL_UPDATESTATISTICSTEMPLATE = "updateStatisticsTemplate"; 
	private static final String URL_DELETESTATISTICSTEMPLATE = "deleteStatisticsTemplate"; 
	private static final String URL_SELECTSTATISTICSTEMPLATE = "selectStatisticsTemplate"; 
	private static final String URL_SELECTSTATISTICSTEMPLATES = "selectStatisticsTemplates"; 
	private static final String URL_SELECTSTATISTICSTEMPLATEPAGE = "selectStatisticsTemplatePage";
	private static final String URL_SELECTBYSQL = "selectBySql";
    private static final String URL_SELECTAUTBYSQL = "selectAutBySql";
    private static final String URL_SELECTTEMPLATEMENUBYOPERIDX="selectTemplateMenuByOperidx";
	

	@Override
	public ResultEntity takeDataSource(String req) {
		return postUrl(URL_TAKEDATASOURCE, req);
	}
	@Override
	public ResultEntity selectBookLocations(String req) {
		return postUrl(URL_BOOKLOCATION, req);
	}

	@Override
	public ResultEntity selectBookCirtypes(String req) {
		return postUrl(URL_BOOKCIRTYPE, req);
	}

	@Override
	public ResultEntity selectBookMediatypes(String req) {
		return postUrl(URL_BOOKMEDIATYPE, req);
	}
	
	@Override
	public ResultEntity selectReadertype(String req) {
		return postUrl(URL_SELECTREADERTYPE, req);
	}
	@Override
	public ResultEntity selectFunMaindatas(String req) {
		return postUrl(URL_SELECTMAINFUNS, req);
	}
	@Override
	public ResultEntity selectFunSubdatas(String req) {
		return postUrl(URL_SELECTSUBFUNS, req);
	}
	@Override
	public ResultEntity insertStatisticsTemplate(String req) {
		String url = requestURL.getRequestURL(URL_INSERTSTATISTICSTEMPLATE);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	@Override
	public ResultEntity updateStatisticsTemplate(String req) {
		String url = requestURL.getRequestURL(URL_UPDATESTATISTICSTEMPLATE);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	@Override
	public ResultEntity deleteStatisticsTemplate(String req) {
		String url = requestURL.getRequestURL(URL_DELETESTATISTICSTEMPLATE);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	@Override
	public ResultEntity queryOneStatisticsTemplate(String req) {
		String url = requestURL.getRequestURL(URL_SELECTSTATISTICSTEMPLATE);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	@Override
	public ResultEntity queryStatisticsTemplates(String req) {
		String url = requestURL.getRequestURL(URL_SELECTSTATISTICSTEMPLATES);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", req);
		String resultJson = HttpClientUtil.doPost(url, map , Consts.UTF_8.toString());
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}
	@Override
	public ResultEntity selectStatisticsTemplatePage(String req) {
		return postUrl(URL_SELECTSTATISTICSTEMPLATEPAGE, req);
	}
	@Override
	public ResultEntity selectBySql(String req) {
		return postUrl(URL_SELECTBYSQL, req);
	}

    @Override
    public ResultEntity selectAutBySql(String req) {
        return postUrl(URL_SELECTAUTBYSQL, req);
    }

    @Override
    public ResultEntity selectTemplateMenuByOperidx(String req) {
        return postUrl(URL_SELECTTEMPLATEMENUBYOPERIDX, req);
    }

}
