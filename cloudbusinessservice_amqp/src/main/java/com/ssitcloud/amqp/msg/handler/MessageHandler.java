package com.ssitcloud.amqp.msg.handler;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.ssitcloud.amqp.sercive.MsgProcessInterface;
import com.ssitcloud.common.system.BeanFactoryHelper;
import com.ssitcloud.common.utils.LogUtils;



/**
 * 消息处理类
 * 
 * @author yeyalin 2017-10-18
 * 
 */
@Component
public class MessageHandler implements ChannelAwareMessageListener {
	public void onMessage(Message message,  Channel channel)
			throws Exception {
		String queue = message.getMessageProperties().getConsumerQueue();
		String msg =  new String( message.getBody(),"GBK");
		LogUtils.info("接收到的信息： " + msg);
		if(msg!=null && !"".equals(msg)){
			MsgProcessInterface dataSyncExecute=BeanFactoryHelper.getBean(queue, MsgProcessInterface.class);
			boolean flag =dataSyncExecute.execute(msg);
//			channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, false);
		}
		
	}

}
