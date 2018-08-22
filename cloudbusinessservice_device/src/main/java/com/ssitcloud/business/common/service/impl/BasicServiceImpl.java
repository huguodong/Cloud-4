package com.ssitcloud.business.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.util.JSONUtils;

import org.apache.http.Consts;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.common.DbServiceException;
import com.ssitcloud.business.common.service.BasicService;
import com.ssitcloud.business.common.util.HttpClientUtil;
import com.ssitcloud.business.common.util.JsonUtils;
import com.ssitcloud.business.common.util.LogUtils;
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
	
	private static final String URL_SelMenuBySsitCloudAdmin = "SelMenuBySsitCloudAdmin";
	
	private static final String URL_SelMenuByOperIdx = "SelMenuByOperIdx";
	
	@Override
	public String getUrl(String urlId){
		return requestURL.getRequestURL(urlId);
	}
	
	@Override
	public String queryPermession(String json){
		String url=requestURL.getRequestURL(QUERY_PERMESSIN_BY_OPER_IDX);
		Map<String,String> map=new HashMap<>();
		map.put("json", json);
		return  HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public boolean havePermession(Integer idx, String... cmd) {
		if(idx == null){
			throw new IllegalArgumentException("idx is null,please check your args");
		}
		if(cmd == null){
			return true;
		}
		final String json = "{\"idx\":#{idx}}";
		String queryPermission = queryPermession(json.replace("#{idx}", idx.toString()));
		if(JSONUtils.mayBeJSON(queryPermission)){
			Map<String, Object> queryPermissionMap = JsonUtils.fromJson(queryPermission, Map.class);
			List<Object> resultList = (List<Object>) queryPermissionMap.get("result");
			if(resultList == null || resultList.isEmpty()){//没有查询到权限
				return false;
			}
			for(int cmdIndex = 0,cmdLength=cmd.length;cmdIndex<cmdLength;++cmdIndex){
				String c = cmd[cmdIndex];
				boolean noState = true;
				for(int i = 0,z=resultList.size();i<z;++i){
					Map<String, String> permissionData = (Map<String, String>) resultList.get(i);
					String queryCmd = permissionData.get("opercmd");//查询用户含有的cmd
					if(c.equals(queryCmd)){
						noState = false;
						break;
					}
				}
				if(noState){
					return false;
				}
			}
		}else{
			throw new DbServiceException("远程服务器没有返回预期的json",requestURL.getRequestURL(QUERY_PERMESSIN_BY_OPER_IDX));
		}
		
		return true;
	}
	
	@Override
	public String SelMenuByOperIdx(String json) {
		String url=requestURL.getRequestURL(URL_SelMenuByOperIdx);
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
	@Override
	public ResultEntity SelMenuBySsitCloudAdmin(String json) {
		ResultEntity result=new ResultEntity();
		String url=requestURL.getRequestURL(URL_SelMenuBySsitCloudAdmin);
		Map<String,String> map=new HashMap<>();
		map.put("json",json);
		String res=HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		if(JSONUtils.mayBeJSON(res)){
			result=JsonUtils.fromJson(res, ResultEntity.class);
		}else{
			result.setRetMessage(res);
		}
		return result;
	}
}
