package com.ssitcloud.amqp.sercive;

import com.ssitcloud.request.entity.InterfaceRequestDto;



/**
 * 保存次请求数据
 * @author yeyalin 2018-3-18
 * 
 */
public interface InterfaceRequestComService {
	
	
	/**
	 * 保存次请求数据
	 * @param msg
	 * @param url,请求的url
	 * @param interfaceRequestUrl 保存请求的url
	 */
	public void  saveRequest(String msg,String url,String interfaceRequestUrl);
	
	/**
	 * 更新请求数据
	 * @param request
	 * @param interfaceRequestUrl
	 */
	public void updateRequest(InterfaceRequestDto request,String interfaceRequestUrl);
	
	
}
