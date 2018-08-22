/*package com.ssitcloud.common.system;

import javax.annotation.Resource;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.xbean.BrokerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jms.connection.SingleConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class AppConfig {
	// 注入一个连接工厂，这个连接工厂bean是在本类中定义的。
	@Resource
	private ActiveMQConnectionFactory connectionFactory;

	// 定义activemq代理的一个实例，这里主要是为了启动一个嵌入式的jms代理（vm）
	@Bean(name = "brokerFactory")
	public BrokerFactoryBean brokerFactoryBean() {
		// 创建代理时，指定代理分析他的配置文档，就是先前我们配置的那个文档
		BrokerFactoryBean bfb = new BrokerFactoryBean(new ClassPathResource("application-activemq.xml"));
		// 设置程序运行时开启代理
		bfb.setStart(true);
		return bfb;
	}

	// 创建一个连接工厂，用于程序连接到activemq代理。
	@Bean(name = "connectionFactory")
	public ActiveMQConnectionFactory connectionFactory() {
		// 指定连接到本地vm的那个代理（注意这个vm就是brokerFactory中启动的那个代理）
		//<property name="trustAllPackages" value="true"/>
		ActiveMQConnectionFactory amqf = new ActiveMQConnectionFactory("vm://localhost");
		amqf.setTrustAllPackages(true);
		return amqf;
	}

	// 创建一个消息队列
	@Bean(name = "queue")
	public ActiveMQQueue queue() {
		ActiveMQQueue q = new ActiveMQQueue();
		q.createDestination("RecevieQueue");
		q.setPhysicalName("com.ssitcloud.activemqTest");
		return q;
	}

	// 创建服务端的jmsTemplate（一般情况下服务端和客户端的jmsTemplate可以设置相同，但为了效率因素，我们将其分开设置，服务端应尽量减少连接数量，所以使用singleConnectionFactory）。
	@Bean(name = "serviceJmsTemplate")
	public JmsTemplate jmsTemplate() {
		JmsTemplate jt = new JmsTemplate();
		SingleConnectionFactory scf = new SingleConnectionFactory();
		scf.setTargetConnectionFactory(connectionFactory);
		jt.setConnectionFactory(scf);		
		return jt;
	}

	// 客户端的jmsTemplate
	@Bean(name = "customJmsTemplate")
	public JmsTemplate jmsTemplateCustom() {
		JmsTemplate jt = new JmsTemplate();
		jt.setConnectionFactory(connectionFactory);
		return jt;
	}
	
}
*/