package com.ssitcloud.amqp.sercive.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.amqp.sercive.InterfaceRequestComService;
import com.ssitcloud.amqp.sercive.TransDataMsgProcessService;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.common.utils.LogUtils;

/**
 * 操作日志
 * 主要是跟图书馆业务相关的日志
 * @author yeyalin 2018-3-18
 * 
 */
@Service
public class TransDataMsgProcessServiceImpl implements TransDataMsgProcessService {
	private static final String CHARSET = "UTF-8";
	private static final String TRANSDATATODATASYNC = "dealTransData";
	private static final String SAVE_INTERFACE_REQUEST = "addRequest";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;
	
	@Resource
	private InterfaceRequestComService interfaceRequestMangementService;

	/**
	 * 
	 * 操作日志消息处理
	 * 
	 * @author yeyalin 2018-3-18
	 * @param msg
	 */
	@Override
	public boolean dealDataMessage(String msg) {
		boolean falg = true;
		try {
			Map<String, String> param = new ConcurrentHashMap<>();
			param.put("req", msg);
			
			String url = requestURL.getRequestURL(TRANSDATATODATASYNC);
			
			System.out.println("---操作日志TransData---请求url=" +url + "-----,请求参数:"+param);
			
			//保存每次请求数据
			String interfaceRequestUrl = requestURL.getRequestURL(SAVE_INTERFACE_REQUEST);
			interfaceRequestMangementService.saveRequest(msg, url, interfaceRequestUrl);
			
			
			HttpClientUtil.doPost(url, param,CHARSET);
			
			System.out.println("-----------transData 异步处理-----------------");
			LogUtils.info("-----------transData 异步处理-----------------");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("-----------transData,操作日志消息处理返回失败,errorMsg:" + e.getMessage());
			LogUtils.error("-----------transData,操作日志消息处理返回失败,errorMsg: " + e.getMessage());
			falg =false;
		}
		return falg;
	}


}
