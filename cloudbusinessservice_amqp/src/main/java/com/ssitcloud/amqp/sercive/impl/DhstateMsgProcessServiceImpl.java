package com.ssitcloud.amqp.sercive.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.amqp.sercive.DhstateMsgProcessService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.utils.LogUtils;

/**
 * 
 * 3D导航状态消息处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
@Service
public class DhstateMsgProcessServiceImpl implements DhstateMsgProcessService {
	private static final String CHARSET = "UTF-8";
	private static final String TRANSDATATOSPECIALDEVICE = "transDataToSpecialDevice";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;

	/**
	 * 
	 * 3D导航状态消息处理
	 * 
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	@Override
	public boolean dealDhstateMessage(String msg) {
		boolean falg = true;
		try {
			Map<String, String> param = new ConcurrentHashMap<>();
			param.put("req", msg);
			String url = requestURL.getRequestURL(TRANSDATATOSPECIALDEVICE);
			System.out.println("---3D导航状态---请求url=" +url + "-----,请求参数，req="+param);
			String response = HttpClientUtil.doPost(url, param,CHARSET);
			if (!"".equals(response) && response != null) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				// 状态：1-成功，0-失败
				boolean status = jsonObj.getBoolean("state");
				// 失败原因
				String errorMsg = jsonObj.getString("message");
				if ("0".equals(status)) {
					LogUtils.info("3D导航状态消息处理返回失败，原因如下： " + errorMsg);
				}
			}else{
				System.out.println("-----------reciceDhstateMessage RllStateMsgVO--3D导航状态消息处理返回失败-----------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			falg =false;
		}
		return falg;
	}

}
