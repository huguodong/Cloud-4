package com.ssitcloud.amqp.sercive.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ssitcloud.amqp.sercive.RllStateMsgProcessService;
import com.ssitcloud.businessauth.utils.HttpClientUtil;
import com.ssitcloud.common.entity.RequestURLListEntity;
import com.ssitcloud.common.utils.LogUtils;

/**
 * 
 * 人流量设备状态处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
@Service
public class RllStateMsgProcessServiceImpl implements RllStateMsgProcessService {
	private static final String CHARSET = "UTF-8";
	private static final String TRANSDATATOSPECIALDEVICE = "transDataToSpecialDevice";
	@Resource(name = "requestURL")
	private RequestURLListEntity requestURL;

	/**
	 * 人流量设备状态消息处理
	 * 
	 * @param msg
	 * @return
	 */
	@Override
	public boolean dealRllStateMessage(String data) {
		boolean flag = true;
		try {
			Map<String, String> param = new ConcurrentHashMap<>();
			param.put("req",  data);
			String url = requestURL.getRequestURL(TRANSDATATOSPECIALDEVICE);
			System.out.println("--人流量设备状态----请求url=" +url + "-----,请求参数:"+param);
			String response = HttpClientUtil.doPost(url, param,CHARSET);
			if (!"".equals(response) && response != null) {
				JSONObject jsonObj = JSONObject.fromObject(response);
				// 状态：1-成功，0-失败
				boolean status = jsonObj.getBoolean("state");
				// 失败原因
				String errorMsg = jsonObj.getString("message");
				if (!status) {
					LogUtils.info("人流量设备状态消息处理返回失败，原因如下： " + errorMsg);
					flag=false;
				}
			}else{
				System.out.println("-----------reciceDhstateMessage RllStateMsgVO--人流量设备状态消息处理返回失败-----------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

}
