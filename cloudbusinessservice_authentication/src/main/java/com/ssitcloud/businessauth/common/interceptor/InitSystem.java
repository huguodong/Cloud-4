package com.ssitcloud.businessauth.common.interceptor;

import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ServletContextAware;

import com.ssitcloud.businessauth.utils.XMLUtils;
import com.ssitcloud.common.entity.RequestURLListEntity;

/**
 * 启动服务的时候调用的方法
 *
 * <p>2016年3月22日 下午5:32:10 
 * @author hjc 
 *
 */
public class InitSystem implements InitializingBean, ServletContextAware {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("系统初始化中....");
		try {
			//初始化进行的操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("初始化完成！");
		
	}

	
	

}
