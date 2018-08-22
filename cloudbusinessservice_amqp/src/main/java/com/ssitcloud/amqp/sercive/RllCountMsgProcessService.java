package com.ssitcloud.amqp.sercive;

import com.ssitcloud.devmgmt.entity.RllCountMsgVO;

/**
 * 
 * 人流量统计消息处理类
 * 
 * @author yeyalin
 * @data 2017年10月18日
 */
public interface RllCountMsgProcessService {
	/**
	 * 
	 * 人流量统计消息处理
	 * 
	 * @author yeyalin
	 * @data 2017年10月18日
	 */
	public boolean dealRllCountMsg(RllCountMsgVO msgVo);
}
