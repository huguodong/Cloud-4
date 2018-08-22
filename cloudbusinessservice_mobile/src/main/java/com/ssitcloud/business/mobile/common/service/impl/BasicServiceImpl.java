package com.ssitcloud.business.mobile.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.service.BasicService;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BasicServiceImpl implements BasicService{
		
	@Resource(name = "requestURL")
	protected  RequestURLListEntity requestURL;
	
	
	@Override
	public ResultEntity postURL(String URL,String req) {
		ResultEntity resultEntity=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(result!=null&&!result.contains(">")){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity postURLLongtime(String URL,String req){
		ResultEntity resultEntity=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result=HttpClientUtil.doPostLongtime(reqURL, map, Consts.UTF_8.toString());
		if(result!=null&&!result.contains(">")){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
	@Override
	public ResultEntity postURL(String URL,Map<String, String> map){
		ResultEntity resultEntity=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL);
		String result=HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(result)){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
}
