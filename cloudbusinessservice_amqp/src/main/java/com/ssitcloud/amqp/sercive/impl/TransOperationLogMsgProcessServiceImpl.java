package com.ssitcloud.amqp.sercive.impl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.amqp.sercive.InterfaceRequestComService;
import com.ssitcloud.amqp.sercive.TransOperationLogMsgProcessService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.request.entity.InterfaceRequestEntity;

/**
 * 操作日志
 * 主要是跟图书馆业务相关的日志
 * @author yeyalin 2018-3-18
 * 
 */
@Service
public class TransOperationLogMsgProcessServiceImpl implements TransOperationLogMsgProcessService {
	private static final String CHARSET = "UTF-8";
	private static final String TRANSOPERATIONLOGTODATASYNC = "dealOperationLog";
	private static final String SAVE_INTERFACE_REQUEST = "addRequest";
	
	@Resource
	private InterfaceRequestComService interfaceRequestMangementService;
	
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;

	/**
	 * 
	 * 操作日志消息处理
	 * 
	 * @author yeyalin 2018-3-18
	 * @param msg
	 */
	@Override
	public boolean dealOperationLogMessage(String msg) {
		boolean falg = true;
		try {
			Map<String, String> param = new ConcurrentHashMap<>();
			param.put("req", msg);
			String url = requestURL.getRequestURL(TRANSOPERATIONLOGTODATASYNC);
			
			System.out.println("--操作日志dealOperationLog----请求url=" +url + "-----,请求参数:"+param);
			//保存每次请求数据
			String interfaceRequestUrl = requestURL.getRequestURL(SAVE_INTERFACE_REQUEST);
			interfaceRequestMangementService.saveRequest(msg, url, interfaceRequestUrl);
			HttpClientUtil.doPost(url, param,CHARSET);
			
			System.out.println("-----------transOperationLog,服务器端异步处理-----------------");
			LogUtils.info("-----------transOperationLog,服务器端异步处理-----------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("-----------transOperationLog,操作日志消息处理返回失败,errorMsg:" + e.getMessage());
			LogUtils.info("-----------transOperationLog,操作日志消息处理返回失败,errorMsg:" + e.getMessage());
			falg =false;
		}
		return falg;
	}

}
