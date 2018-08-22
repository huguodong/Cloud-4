/*package com.ssitcloud.common.servlet;

import java.io.IOException;

import javax.jms.TextMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.http.Consts;
import org.springframework.jms.core.JmsTemplate;

import com.ssitcloud.common.util.LogUtils;
*//**
 * 用于接收从设备端请求的数据，并将JSON格式的数据存储到消息队列<br/>
 * 队列的名称标识在config.properties中定义 key=QUEUE_NAME<br/>
 * 
 * @package: com.ssitcloud.common.servlet
 * @classFile: DataReceiveServlet
 * @author: liuBh
 * @description: TODO
 *//*
@WebServlet(value="/datasync",loadOnStartup=1,asyncSupported=true)
public class DataReceiveServlet extends BasicServlet{
	
	private static final long serialVersionUID = -4826270409834315880L;
	private static String QUEUE_NAME;
	private JmsTemplate jmsTemplate;
	@Override
	public void init(ServletConfig servletConfig)throws ServletException {
		super.init(servletConfig);
		jmsTemplate=getBean("serviceJmsTemplate", JmsTemplate.class);
		QUEUE_NAME=(String) configs.get("QUEUE_NAME");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {
			//req.setCharacterEncoding("UTF-8");
			String re=req.getParameter("req");
			if(re!=null&&!"".equals(re)){
				re=new String(re.getBytes(Consts.ISO_8859_1),"UTF-8");
				if(jmsTemplate!=null){
					jmsTemplate.setDefaultDestinationName(QUEUE_NAME);
					jmsTemplate.convertAndSend(re);
				}else{
					throw new RuntimeException("DataReceiveServlet.java jmsTemplate is null");
				}
			}
		} catch (Exception e) {
			LogUtils.error("接收数据发生错误", e);
			//e.printStackTrace();
		}
	}

	

}
*/