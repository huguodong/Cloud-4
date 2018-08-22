package com.ssitcloud.business.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;






import com.ssitcloud.business.common.service.BasicService;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.ResultEntityF;

@Service("basicServiceImpl")
public class BasicServiceImpl implements BasicService{
		
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	public static final String charset=Consts.UTF_8.toString();
	
	public static final String QUERY_PERMESSIN_BY_OPER_IDX="SelUserCmdsByIdx";
	
	public static final String URL_SelectMetaOperCmd="SelectMetaOperCmd";
	
	public static final String URL_SelUserCmdsByIdxAndRestriDevice="SelUserCmdsByIdxAndRestriDevice";

	private static final String URL_SelPermissionBySsitCloudAdmin = "SelPermissionBySsitCloudAdmin";
	
	@Override
	public String queryPermession(String json){
		String url=requestURL.getRequestURL(QUERY_PERMESSIN_BY_OPER_IDX);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	@Override
	public String queryPermessionByDevice(String req){
		String url=requestURL.getRequestURL(URL_SelUserCmdsByIdxAndRestriDevice);
		Map<String,String> map=new HashMap<>();
		map.put("req", req);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public String queryMetaOperCmd(){
		String url=requestURL.getRequestURL(URL_SelectMetaOperCmd);
		Map<String,String> map=new HashMap<>();
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
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
	
	@Override
	public ResultEntity SelPermissionBySsitCloudAdmin(String req) {
		ResultEntity result=new ResultEntity();
		String url=requestURL.getRequestURL(URL_SelPermissionBySsitCloudAdmin);
		Map<String,String> map=new HashMap<>();
		map.put("req",req);
		String res=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(res)){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}else{
			result.setRetMessage(res);
		}
		return result;
	}
	
}
