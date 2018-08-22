package com.ssitcloud.business.mobile.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.ssitcloud.business.mobile.common.util.HttpClientUtil;
import com.ssitcloud.business.mobile.common.util.JsonUtils;
import com.ssitcloud.business.mobile.common.util.LogUtils;
import com.ssitcloud.business.mobile.operationEntity.AbstractOperationLog;
import com.ssitcloud.business.mobile.service.OperationLogServiceI;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.entity.ResultEntity;
import com.ssitcloud.mobile.entity.OperationLogEntity;

/**
 * 
 * @author LXP
 * @version 创建时间：2017年3月4日 下午5:01:06
 */
@Service
public class OperationLogServiceImpl implements OperationLogServiceI {
	private final String URL_ADD_LOG = "AddOperationLog";
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURLEntity;
	private static final String charset = "utf-8";

	@Async
	@Override
	public Boolean addOperationLog(AbstractOperationLog operationLog) {
		if(operationLog == null){
			return false;
		}
		//转换，保证输出数据能与db层对接
		String temp = JsonUtils.toJson(operationLog);
		OperationLogEntity ole = JsonUtils.fromJson(temp, OperationLogEntity.class);
		
		String url = requestURLEntity.getRequestURL(URL_ADD_LOG);
		Map<String, String> map = new HashMap<>(2);
		map.put("json", JsonUtils.toJson(ole));
		String remoteJson = HttpClientUtil.doPost(url, map , charset);
		if(remoteJson != null){
			try{
				ResultEntity resultEntity = JsonUtils.fromJson(remoteJson, ResultEntity.class);
				return resultEntity.getState();
			}catch(Exception e){
				LogUtils.error(OperationLogServiceImpl.class+".addOperationLog从"+url+"获取到的数据不是预期数据");
			}
		}
		return false;
	}

}
