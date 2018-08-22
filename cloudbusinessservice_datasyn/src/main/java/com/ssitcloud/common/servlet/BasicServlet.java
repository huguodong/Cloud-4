/*package com.ssitcloud.common.servlet;

import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ssitcloud.common.exception.CommonException;

@WebServlet(value="/basic",loadOnStartup=1)
public class BasicServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	protected WebApplicationContext ctx;
	protected Properties configs;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext servletContext = config.getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		if(ctx==null){
			throw new CommonException("BasicServlet.java 启动异常 WebApplicationContext is null "+new Date());
		}
		configs=ctx.getBean("propFactory", Properties.class);
		if(configs==null){
			throw new CommonException("BasicServlet.java 启动异常 Properties is null \n");
		}
	}

	public <T> T getBean(String beanName,Class<T> clazz){
		if(ctx!=null){
			return ctx.getBean(beanName, clazz);
		}else{
			throw new CommonException("BasicServlet.java 启动异常 "+new Date()); 
		}
	}
	public  Object getBean(String beanName){
		if(ctx!=null){
			return ctx.getBean(beanName);
		}else{
			throw new CommonException("BasicServlet.java 启动异常 "+new Date()); 
		}
	}
	
	
}
*/