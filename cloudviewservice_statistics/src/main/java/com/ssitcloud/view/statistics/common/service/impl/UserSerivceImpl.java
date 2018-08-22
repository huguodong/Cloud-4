package com.ssitcloud.view.statistics.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Consts;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.view.statistics.common.service.UserService;
import com.ssitcloud.view.statistics.common.util.HttpClientUtil;
import com.ssitcloud.view.statistics.common.util.JsonUtils;
import com.ssitcloud.view.statistics.common.util.LogUtils;

@Service
public class UserSerivceImpl extends BasicServiceImpl implements UserService{

	private static final String URL_SelPermissionBySsitCloudAdmin = "SelPermissionBySsitCloudAdmin";
	private static final String URL_SelMenuBySsitCloudAdmin = "SelMenuBySsitCloudAdmin";
	


	@Override
	public String logincheck(String json){
		if(StringUtils.isEmpty(json)) return null;
		String url=requestURL.getRequestURL("loginCheck");
		Map<String,String> map=new HashMap<>();
		map.put("operInfo", json);
		String res = HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
		LogUtils.info("UserSerivceImpl.java :从验证服务器返回数据:"+res);
		return res;
	}
	@Override
	public ResultEntity logincheckRetunEntity(UserEntity user){
		String res=logincheck(JsonUtils.toJson(user));
		if(StringUtils.hasText(res)){
			return JsonUtils.fromJson(res, ResultEntity.class);
		}
		return null;
	}
	@Override
	public String SelPermissionByOperIdx(String idx){
		Map<String,String> map=new HashMap<>();
		map.put("json", "{\"idx\":"+idx+"}");
		String url=requestURL.getRequestURL("SelPermissionByOperIdx");
		return HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public String SelMenuByOperIdx(String idx,String flags) {
		Map<String,String> map=new HashMap<>();
		map.put("json", "{\"idx\":"+idx+",\"flags\":"+flags+"}");
		String url=requestURL.getRequestURL("SelMenuByOperIdx");
		return HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public String SelMetaOperCmd(){//SelectMetaOperCmd
		Map<String,String> map=new HashMap<>();
		String url=requestURL.getRequestURL("SelectMetaOperCmd");
		return HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
	@Override
	public String SelPermissionBySsitCloudAdmin() {
		return postUrlReturnStr(URL_SelPermissionBySsitCloudAdmin, "");
	}
	@Override
	public String SelMenuBySsitCloudAdmin(String flags) {
		Map<String,String> map=new HashMap<>();
		map.put("json", "{\"flags\":"+flags+"}");
		String url=requestURL.getRequestURL(URL_SelMenuBySsitCloudAdmin);
		return HttpClientUtil.doPost(url, map, Consts.UTF_8.toString());
	}
	
}
