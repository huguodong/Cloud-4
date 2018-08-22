package com.ssitcloud.business.task.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.task.common.service.BasicService;
import com.ssitcloud.business.task.common.util.HttpClientUtil;
import com.ssitcloud.business.task.common.util.JsonUtils;
import com.ssitcloud.business.task.common.util.ResourcesUtil;
import com.ssitcloud.business.task.common.util.XMLUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class BasicServiceImpl implements BasicService{
		
	@Resource(name = "requestURL")
	protected  RequestURLListEntity requestURL;
	
	
	@Override
	public ResultEntity postURL(String URL,String req) {
		ResultEntity resultEntity=new ResultEntity();
		if(requestURL==null)//在线程里重新调用时，得不到RequestURLListEntity的实例，故重新实例化一次 modify by huanghuang 20170227
			requestURL = new RequestURLListEntity(XMLUtils.parseAll(ResourcesUtil.getInputStream("RequestURL.xml")));
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
	
}
