package com.ssitcloud.amqp.sercive;



/**
 * 操作日志
 * @author yeyalin 2018-3-18
 * 
 */
public interface TransDataMsgProcessService {
	/**
	 * 
	 * 操作日志消息处理
	 * 
	 * @author yeyalin 2018-3-18
	 * @param msg
	 */
	public boolean dealDataMessage(String msg);
}
