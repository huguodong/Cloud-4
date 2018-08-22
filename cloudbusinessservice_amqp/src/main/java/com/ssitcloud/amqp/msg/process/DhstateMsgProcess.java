package com.ssitcloud.amqp.msg.process;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ssitcloud.amqp.sercive.MsgProcessInterface;
import com.ssitcloud.amqp.sercive.impl.DhstateMsgProcessServiceImpl;

/**
 * 3D导航消息处理
 * 
 * @author yeyalin 2017-10-18
 * 
 */
@Component(value="DhState")
public class DhstateMsgProcess extends DhstateMsgProcessServiceImpl implements MsgProcessInterface{

	/**
	 * 处理3D导航消息
	 * @param msg
	 * @return
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean execute(String msg) {
		return dealDhstateMessage(msg);
	}


}
