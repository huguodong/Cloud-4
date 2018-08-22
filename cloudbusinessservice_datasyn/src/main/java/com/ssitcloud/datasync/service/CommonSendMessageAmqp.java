package com.ssitcloud.datasync.service;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;

/**
 * 发送消息到rabbitmq
 * @author yeyaln
 *
 */
public interface CommonSendMessageAmqp {
	/* 
	 * 发送消息到rabbitmq
	 * 
	 * @param RequestEntity requestEntity
	 * @param exchange rabbitmq交换机
	 * @param routingKey 路由键
	 */
	public RespEntity send(RequestEntity requestEntity,String exchange,String routingKey);
	/* 
	 * 发送消息到rabbitmq
	 * 
	 * @param RequestEntity requestEntity
	 * @param opetation 操作
	 */
	public RespEntity send(RequestEntity requestEntity,String operation);
}
