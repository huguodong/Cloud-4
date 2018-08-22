package com.ssitcloud.business.mobile.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.PublicKeyServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class PublicKeyServiceImpl implements PublicKeyServiceI {
	private String URL_SELECT_KEY="getAllUsefulAppVersionInfo";
	
	@Resource(name="requestURL")
	protected  RequestURLListEntity requestURL;
	
	@Override
	public ResultEntity selectNewPublic(Integer appType) {
		ResultEntity resultEntity = new ResultEntity();
		String url = requestURL.getRequestURL(URL_SELECT_KEY);
		
		try{
			String remoteJson = HttpClientUtil.doPost(url, new HashMap<String,String>(), "utf-8");
			ResultEntity remoteResultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
			if(remoteResultEntity.getState()){
				List<Map<String, Object>> keys = (List<Map<String, Object>>) remoteResultEntity.getResult();
				
				Collections.sort(keys,new Comparator<Map<String, Object>>() {

					@Override
					public int compare(Map<String, Object> o1, Map<String, Object> o2) {
						Long t1 = (Long) o1.get("createtime");
						Long t2 = (Long) o2.get("createtime");
						if(t1 == null){
							return -1;
						}else if(t2 == null){
							return 1;
						}
						return t1.compareTo(t2);
					}
				});
				Map<String, Object> publicKey = null;
				for (Map<String, Object> map : keys) {
					if(appType.equals(map.get("app_type"))){
						publicKey = new HashMap<>(3,1.0f);
						if(map.get("publickey") != null){
							publicKey.put("publickey", map.get("publickey"));
							publicKey.put("key_version", map.get("key_version"));
							publicKey.put("createtime", map.get("createtime"));
							resultEntity.setState(true);
							resultEntity.setResult(publicKey);
						}
					}
				}
			}
		}catch(Exception e){
			LogUtils.info(url+" 返回数据不是预期数据",e);
		}
		
		return resultEntity;
	}

}
