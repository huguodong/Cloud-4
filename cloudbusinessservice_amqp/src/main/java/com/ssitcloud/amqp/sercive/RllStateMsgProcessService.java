package com.ssitcloud.amqp.sercive;

import java.util.Map;


/**
 * 
 * 人流量设备状态处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
public interface RllStateMsgProcessService {
	
	/**
	 * 人流量设备状态消息处理
	 * @param data
	 * @return
	 */
	public boolean dealRllStateMessage(String data);
}
