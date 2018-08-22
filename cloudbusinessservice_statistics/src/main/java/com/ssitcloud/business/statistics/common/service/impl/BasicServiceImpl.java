package com.ssitcloud.business.statistics.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.statistics.common.service.BasicService;
import com.ssitcloud.business.statistics.common.utils.HttpClientUtil;
import com.ssitcloud.business.statistics.common.utils.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BasicServiceImpl extends BasicJestServiceImpl implements BasicService{
		
	@Resource(name = "requestURL")
	protected  RequestURLListEntity requestURL;
	
	
	@Override
	public ResultEntity postURL(String URL,String req) {
		ResultEntity resultEntity=new ResultEntity();
		String reqURL=requestURL.getRequestURL(URL);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		String result = HttpClientUtil.doPost(reqURL, map, Consts.UTF_8.toString());
		if(result!=null){
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
		String result = HttpClientUtil.doPostLongtime(reqURL, map, Consts.UTF_8.toString());
		if(result!=null){
			resultEntity=JsonUtils.fromJson(result, ResultEntity.class);
		}
		return resultEntity;
	}
	
}
