package com.ssitcloud.business.mobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.service.FeedbackReplyServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.FeedbackReplyEntity;

@Service
public class FeedbackReplyServiceImpl implements FeedbackReplyServiceI {
	private final String URL_INSERT="addFeedBackReply";
	private final String URL_UPDATE="updateFeedBackReply";
	private final String URL_DELETE="deleteFeedBackReply";
	private final String URL_QUERY_PK="selectFeedBackReply";
	private final String URL_QUERY="selectFeedBackReplys";
	private final String CHARSET = Consts.UTF_8.toString();
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity insertFeedbackReply(String feedbackReplyEntityJson) {
		String url = requestURL.getRequestURL(URL_INSERT);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", feedbackReplyEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public ResultEntity updateFeedbackReply(String feedbackReplyEntityJson) {
		String url = requestURL.getRequestURL(URL_UPDATE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", feedbackReplyEntityJson);
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		return resultEntity;
	}

	@Override
	public boolean deleteFeedbackReply(Integer feedbackReplyEntityPk) {
		//初始化查询参数
		FeedbackReplyEntity fbre = new FeedbackReplyEntity();
		fbre.setReply_idx(feedbackReplyEntityPk);
		
		String url = requestURL.getRequestURL(URL_DELETE);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(fbre));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return resultEntity.getState();
	}

	@Override
	public FeedbackReplyEntity queryOneFeedbackReply(Integer feedbackReplyEntityPk) {
		//初始化查询参数
		FeedbackReplyEntity fbre = new FeedbackReplyEntity();
		fbre.setReply_idx(feedbackReplyEntityPk);
		
		String url = requestURL.getRequestURL(URL_QUERY_PK);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", JsonUtils.toJson(fbre));
		String resultJson = HttpClientUtil.doPostLongtime(url, map, CHARSET);
		if(resultJson == null){
			throw new IllegalArgumentException("向db层请求数据失败");
		}
		ResultEntity resultEntity = JsonUtils.fromJson(resultJson, ResultEntity.class);
		
		return mapToEntity((Map<String, Object>) resultEntity.getResult());
	}

	@Override
	public List<FeedbackReplyEntity> queryFeedbackReplyReplys(String feedbackReplyQueryEntityJson) {
		String url = requestURL.getRequestURL(URL_QUERY);
		Map<String, String> map = new HashMap<>(1);
		map.put("json", feedbackReplyQueryEntityJson);
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
		List<FeedbackReplyEntity> data = new ArrayList<>(length);
		for(int i = 0,z = length;i<z;++i){
			data.add(mapToEntity(result.get(i)));
		}
		return data;
	}
	
	private FeedbackReplyEntity mapToEntity(Map<String, Object> map){
		String json = JsonUtils.toJson(map);
		return JsonUtils.fromJson(json, FeedbackReplyEntity.class);
	}
}
