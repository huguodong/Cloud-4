package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.authentication.entity.SoxTemplateEntity;
import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.service.SoxTemplateServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;

@Service
public class SoxTemplateServiceImpl implements SoxTemplateServiceI{
	final String URL_SELECT= "soxTemplateEntityById";
	
	@Resource(name="requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";
	
	@Override
	public SoxTemplateEntity getSoxTemplateEntityById(Integer sox_tpl_id) {
		String url = requestURLEntity.getRequestURL(URL_SELECT);
		Map<String, String> map = new HashMap<>(3);
		map.put("sox_tpl_id", sox_tpl_id.toString());
		String result = HttpClientUtil.doPost(url, map , charset);
		ResultEntity resultEntity = JsonUtils.fromJson(result, ResultEntity.class);
		if(resultEntity == null || !resultEntity.getState()){
			LogUtils.error(getClass()+" 向远程服务器获取SoxTemplateEntity失败，请检查是否存在数据sox_tpl_id==》"+sox_tpl_id+" url==>"+url);
			return null;
		}
		if(resultEntity.getResult() == null){
			return null;
		}
		
		try{
			String soxTemplJson = JsonUtils.toJson(resultEntity.getResult());
			SoxTemplateEntity soxTemplateEntity = JsonUtils.fromJson(soxTemplJson, SoxTemplateEntity.class);
			return soxTemplateEntity;
		}catch(Exception e){
			LogUtils.error(getClass()+" 没有获取到期望的数据，sox_tpl_id==》"+sox_tpl_id+" url==>"+url);
			return null;
		}
		
	}

}
