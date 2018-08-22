package com.ssitcloud.dblib.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;
import com.ssitcloud.dblib.common.service.BasicService;
import com.ssitcloud.dblib.common.utils.HttpClientUtil;
import com.ssitcloud.dblib.common.utils.JsonUtils;
import com.ssitcloud.dblib.common.utils.LogUtils;

@Service("basicServiceImpl")
public class BasicServiceImpl implements BasicService{
		
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	public static final String charset=Consts.UTF_8.toString();

	@Override
	public String getUrl(String urlId){
		return requestURL.getRequestURL(urlId);
	}
	
	
	@Override
	public String postUrlRetStr(String postUrl,String req){
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			return result;
		}else{
			LogUtils.error(result);
			return null;
		}
		
	}
	@Override
	public ResultEntity postUrl(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	@Override
	public ResultEntity postUrl(String postUrl,String reqContent,String reqName){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put(reqName,reqContent);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	@Override
	public ResultEntity postUrlLongtime(String postUrl,String req){
		ResultEntity resultEntity=new ResultEntity();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPostLongtime(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
	@Override
	public <T> ResultEntityF<T> postUrlF(String postUrl,String req){
		ResultEntityF<T> resultEntity=new ResultEntityF<T>();
		String url=requestURL.getRequestURL(postUrl);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String result=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, new TypeReference<ResultEntityF<T>>() {});
		}else{
			resultEntity.setRetMessage(result);
		}
		return resultEntity;
	}
}
