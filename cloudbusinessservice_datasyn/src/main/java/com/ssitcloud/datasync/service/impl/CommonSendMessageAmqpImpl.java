package com.ssitcloud.datasync.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ssitcloud.common.entity.RequestEntity;
import com.ssitcloud.common.entity.RespEntity;
import com.ssitcloud.common.util.JsonUtils;
import com.ssitcloud.common.utils.LogUtils;
import com.ssitcloud.datasync.service.CommonSendMessageAmqp;

/**
 * 发送消息到rabbitmq
 * @author yeyalin
 *
 */
@Service
public class CommonSendMessageAmqpImpl implements CommonSendMessageAmqp {
	
	/**
	 * key为operation,value为exchange
	 */
	public static Map<String,String> opetationMap = null;
	
	/**
	 * key为operation,value为routingKey
	 */
	public static Map<String,String> routingKeyMap = null;
	
	static
	{
		opetationMap = new HashMap<String, String>();
		routingKeyMap = new HashMap<String, String>();
		
		opetationMap.put("transData","transDataExchange");
		opetationMap.put("transOperationLog","transOperationLogExchange");
		
		routingKeyMap.put("transData","transData");
		routingKeyMap.put("transOperationLog","transOperationLog");
		
	}
	
	@Resource(name = "template")
	private RabbitTemplate rabbitTemplate;

	/* 
	 * 发送消息到rabbitmq
	 * 
	 * @param RequestEntity requestEntity
	 * @param exchange rabbitmq交换机
	 * @param routingKey 路由键
	 */
	@Override
	public RespEntity send(RequestEntity requestEntity,String exchange,String routingKey) {
		MessageProperties props = MessagePropertiesBuilder.newInstance()
			    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
			    .build();
		RespEntity resp = new RespEntity(requestEntity);
		resp.getData().setState(true);
		String msg = "";
		try {
			
			msg =JsonUtils.object2String(requestEntity);
			if(StringUtils.isEmpty(msg)){
				
				return resp;
			}
			LogUtils.info("send message to rabbitmq,msg:"+msg.getBytes("GBK"));
			System.out.println("send message to rabbitmq,msg:"+msg.getBytes("GBK"));
			Message message = MessageBuilder.withBody(msg.getBytes("GBK"))
					.andProperties(props)
					.build();
			
			rabbitTemplate.convertAndSend(exchange, routingKey, message);
		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("发送消息到rabbitmq时,json格式转换出现异常,error: " + e.getMessage() );
			LogUtils.error("发送消息到rabbitmq时,json格式转换出现异常,error: " + e.getMessage() );
		}
		
		return resp;
	}

	/* 
	 * 发送消息到rabbitmq
	 * 
	 * @param RequestEntity requestEntity
	 * @param opetation 操作
	 */
	@Override
	public RespEntity send(RequestEntity requestEntity, String operation) {
		
		return send(requestEntity, opetationMap.get(operation), routingKeyMap.get(operation));
		
	}

}
