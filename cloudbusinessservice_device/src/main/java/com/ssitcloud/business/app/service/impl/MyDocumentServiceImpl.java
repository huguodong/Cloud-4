package com.ssitcloud.business.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.app.service.MyDocumentServiceI;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.MyDocumentEntity;
@Service
public class MyDocumentServiceImpl implements MyDocumentServiceI {
	private final String URL_INSERT="addMyDocument";
	private final String URL_UPDATE="updateMyDocument";
	private final String URL_DELETE="deleteMyDocument";
	private final String URL_QUERY_PK="selectMyDocument";
	private final String URL_QUERY="selectMyDocuments";
	private final String CHARSET = Consts.UTF_8.toString();
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity insertMyDocument(String myDocumentEntityJson) {
		String url = requestURL.getRequestURL(URL_INSERT);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", myDocumentEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public ResultEntity updateMyDocument(String myDocumentEntityJson) {
		String url = requestURL.getRequestURL(URL_UPDATE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", myDocumentEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public boolean deleteMyDocument(Integer myDocumentEntityPk) {
		//初始化查询参数
		MyDocumentEntity mde = new MyDocumentEntity();
		mde.setDocument_idx(myDocumentEntityPk);
		
		String url = requestURL.getRequestURL(URL_DELETE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(mde));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return resultEntity.getState();
	}

	@Override
	public MyDocumentEntity queryOneMyDocument(Integer myDocumentEntityPk) {
		//初始化查询参数
		MyDocumentEntity mde = new MyDocumentEntity();
		mde.setDocument_idx(myDocumentEntityPk);
		
		String url = requestURL.getRequestURL(URL_QUERY_PK);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(mde));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return mapToEntity((Map<String, Object>) resultEntity.getResult());
	}

	@Override
	public List<MyDocumentEntity> queryMyDocuments(String myDocumentQueryEntityJson) {
		String url = requestURL.getRequestURL(URL_QUERY);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", myDocumentQueryEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		List<Map<String, Object>> result =(List<Map<String, Object>>) resultEntity.getResult();
		List<MyDocumentEntity> data = new ArrayList<>(result.size());
		for(int i = 0,z = result.size();i<z;++i){
			data.add(mapToEntity(result.get(i)));
		}
		return data;
	}

	
	private MyDocumentEntity mapToEntity(Map<String, Object> map){
		String json = JsonUtils.toJson(map);
		return JsonUtils.fromJson(json, MyDocumentEntity.class);
	}
}
