package com.ssitcloud.common.service.impl;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ssitcloud.common.service.InterfaceRequestComService;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.utils.HttpClientUtil;
import com.ssitcloud.request.entity.InterfaceRequestDto;

@Service
public class InterfaceRequestComServiceImpl implements InterfaceRequestComService {

	private static final String CHARSET = "UTF-8";
	
	@Override
	public void saveRequest(String msg, String url, String interfaceRequestUrl) {


		if(StringUtils.isEmpty(msg)){
			System.out.println("保存每次请求时，请求数据为空");
			return  ;
					
		}
		
		JSONObject gcondition = JSONObject.fromObject(msg);
		
		if (null == gcondition){
			return  ;
		}
		
		String requestId = gcondition.optString("requestId","");
		
		if(StringUtils.isBlank(requestId) || "null".equals(requestId)){
			
			System.out.println("保存每次请求时，requestId没有值");
			return  ;
		}
		
		try {
				String localIp = InetAddress.getLocalHost().getHostAddress().toString();
				Timestamp requestTime = new Timestamp(System.currentTimeMillis());
				InterfaceRequestDto request = new InterfaceRequestDto();
				
				request.setRequestId(requestId);
				request.setRequestTime(requestTime);
				request.setRequestUrl(url);
				request.setRequestBody(msg);
				//0-未处理：默认状态，1-异常状态,2--正常状态
				request.setResponseStatus(0);
				request.setLocalIp(localIp);
				request.setRequestType("POST");
	
				Map<String, String> param = new ConcurrentHashMap<>();
				param.put("req", JsonUtils.object2String(request));
				
				HttpClientUtil.doPost(interfaceRequestUrl, param,CHARSET);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}

	@Override
	/**
	 * 更新请求数据
	 * @param request
	 * @param interfaceRequestUrl
	 */
	public void updateRequest(InterfaceRequestDto request,String interfaceRequestUrl) {
		Map<String, String> param = new ConcurrentHashMap<>();
		try {
			param.put("req", JsonUtils.object2String(request));
			HttpClientUtil.doPost(interfaceRequestUrl, param,CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 删除请求数据
	 * @param request
	 * @param interfaceRequestUrl
	 */
	@Override
	public void deleteRequest(InterfaceRequestDto request,
			String interfaceRequestUrl) {
		Map<String, String> param = new ConcurrentHashMap<>();
		try {
			param.put("req", JsonUtils.object2String(request));
			HttpClientUtil.doPost(interfaceRequestUrl, param,CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
