package com.ssitcloud.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.service.BasicService;
import com.ssitcloud.common.util.HttpClientUtil;
import com.ssitcloud.common.util.JsonUtils;

@Service
public class BasicServiceImpl implements BasicService{
	@Resource(name="requestURLListEntity")
	protected RequestURLListEntity requestURL;
	
	public static final String charset=Consts.UTF_8.toString();
	
	@Override
	public String getUrl(String urlId){
		return requestURL.getRequestURL(urlId);
	}
	
	@Override
	public ResultEntity postUrl(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, charset);
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setState(false);
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	@Override
	public ResultEntity postUrlSSL(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPostSSL(url, map, charset);
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setState(false);
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
	@Override
	public String postUrlReturnStr(String postUrl,String req){
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, charset);
		return result;
	}
	@Override
	public ResultEntity postUrlLongTimeout(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPostLongTimeout(url, map, charset);
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setState(false);
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	
}
