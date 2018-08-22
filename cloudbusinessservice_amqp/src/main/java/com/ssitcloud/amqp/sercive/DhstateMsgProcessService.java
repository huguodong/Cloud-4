package com.ssitcloud.amqp.sercive;

import java.util.Map;


/**
 * 
 * 3D导航状态消息处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
public interface DhstateMsgProcessService {
	/**
	 * 
	 * 人流量统计消息处理
	 * 
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean dealDhstateMessage(String msg);
}
