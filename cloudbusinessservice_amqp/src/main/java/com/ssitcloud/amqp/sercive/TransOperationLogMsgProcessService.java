package com.ssitcloud.amqp.sercive;

import java.util.Map;


/**
 * 操作日志
 * 主要是跟图书馆业务相关的日志
 * @author yeyalin 2018-3-18
 * 
 */
public interface TransOperationLogMsgProcessService {
	/**
	 * 
	 * 操作日志消息处理
	 * 
	 * @author yeyalin 2018-3-18
	 * @param msg
	 */
	public boolean dealOperationLogMessage(String msg);
}
