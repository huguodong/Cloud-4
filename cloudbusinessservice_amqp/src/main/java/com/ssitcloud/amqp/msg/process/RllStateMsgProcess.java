package com.ssitcloud.amqp.msg.process;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ssitcloud.amqp.sercive.MsgProcessInterface;
import com.ssitcloud.amqp.sercive.impl.RllStateMsgProcessServiceImpl;

/**
 * 3D导航消息处理
 * 
 * @author yeyalin 2017-10-18
 * 
 */
@Component(value="RllState")
public class RllStateMsgProcess extends RllStateMsgProcessServiceImpl implements MsgProcessInterface{

	/**
	 * *处理人流量状态消息
	 * 消息格式：
	 * {
		  "device_id": "RLL_001",
		  "library_id": "QJTSG",
		  "content": {
		    "soft_state": {
		      "Function": {
		        "00040103": "1",
		        "00040101": "0",
		        "00040102": "0",
		        "00040104": "0"
		      },
		      "Main": {
		        "StartupTime": "2017-05-04 14:09:26",
		        "UpdateTime": "2017-05-04 16:20:13"
		      }
		    },
		    "ext_state": {
		      "RLL_DEV_1": "0",
		      "RLL_DEV_2": "1",
		      "RLL_DEV_3": "0",
		      "RLL_DEV_4": "0"
		    }
		  }
		}
	 * @param msg
	 * @return
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean execute(String msg) {
		return dealRllStateMessage(msg);
	}


}
