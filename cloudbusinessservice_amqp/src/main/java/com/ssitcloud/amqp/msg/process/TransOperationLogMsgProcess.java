package com.ssitcloud.amqp.msg.process;

import org.springframework.stereotype.Component;

import com.ssitcloud.amqp.sercive.MsgProcessInterface;
import com.ssitcloud.amqp.sercive.impl.TransOperationLogMsgProcessServiceImpl;

/**
 * 上传操作日志到Mongodb 
 * 主要是跟图书馆业务相关的日志
 * @author yeyalin 2018-3-18
 * 
 */
@Component(value="transOperationLogQueue")
public class TransOperationLogMsgProcess extends TransOperationLogMsgProcessServiceImpl implements MsgProcessInterface{

	/**
	 * 处理3D导航消息
	 * @param msg
	 * @return
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean execute(String msg) {
		
		return dealOperationLogMessage(msg);
	}


}
