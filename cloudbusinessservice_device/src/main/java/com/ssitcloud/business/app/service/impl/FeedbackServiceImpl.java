package com.ssitcloud.business.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.app.service.FeedbackServiceI;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年2月23日 上午10:29:15
 */
@Service
public class FeedbackServiceImpl implements FeedbackServiceI {
	private final String URL_INSERT="addFeedBack";
	private final String URL_UPDATE="updateFeedBack";
	private final String URL_DELETE="deleteFeedBack";
	private final String URL_QUERY_PK="selectFeedBack";
	private final String URL_QUERY="selectFeedBacks";
	private final String CHARSET = Consts.UTF_8.toString();
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity insertFeedback(String FeedBackEntityJson) {
		String url = requestURL.getRequestURL(URL_INSERT);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", FeedBackEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity;
		if(resultJson == null){
			resultEntity = new ResultEntity();
			resultEntity.setValue(false, "向db层请求数据失败");
		}else{
			resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		}
		return resultEntity;
	}

	@Override
	public ResultEntity updateFeedback(String FeedBackEntityJson) {
		String url = requestURL.getRequestURL(URL_UPDATE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", FeedBackEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity;
		if(resultJson == null){
			resultEntity = new ResultEntity();
			resultEntity.setValue(false, "向db层请求数据失败");
		}else{
			resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		}
		return resultEntity;
	}

	@Override
	public boolean deleteFeedback(Integer FeedBackEntityPk) {
		//初始化查询参数
		FeedbackEntity fbe = new FeedbackEntity();
		fbe.setFeedback_idx(FeedBackEntityPk);
		
		String url = requestURL.getRequestURL(URL_DELETE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(fbe));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return resultEntity != null?resultEntity.getState():false;
	}

	@Override
	public FeedbackEntity queryOneFeedback(Integer FeedBackEntityPk) {
		//初始化查询参数
		FeedbackEntity fbe = new FeedbackEntity();
		fbe.setFeedback_idx(FeedBackEntityPk);
		
		String url = requestURL.getRequestURL(URL_QUERY_PK);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(fbe));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return mapToEntity((Map<String, Object>) resultEntity.getResult());
	}

	@Override
	public List<FeedbackEntity> queryFeedbacks(String FeedBackQueryEntityJson) {
		String url = requestURL.getRequestURL(URL_QUERY);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", FeedBackQueryEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		//检查对方返回值
		Object resultObj = resultEntity.getResult();
		List<Map<String, Object>> result = null;
		if(resultObj instanceof List){
			result = (List<Map<String, Object>>) resultEntity.getResult();
		}
		
		int length = result != null?result.size():0;
		List<FeedbackEntity> data = new ArrayList<>(length);
		for(int i = 0,z = length;i<z;++i){
			data.add(mapToEntity(result.get(i)));
		}
		return data;
	}

	private FeedbackEntity mapToEntity(Map<String, Object> map){
		String json = JsonUtils.toJson(map);
		return JsonUtils.fromJson(json, FeedbackEntity.class);
	}
}
