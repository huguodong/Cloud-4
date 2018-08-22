package com.ssitcloud.amqp.sercive;


/**
 * 
 * 消息处理接口
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
public interface MsgProcessInterface {
	/**
	 * 消息处理接口
	 * @param msg
	 * @return
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean execute(String msg);
}
