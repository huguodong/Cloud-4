package com.ssitcloud.dbauth.common.interceptor;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.ssitcloud.dbauth.service.LibraryService;

/**
 * 启动服务的时候调用的方法
 *
 * <p>2016年3月22日 下午5:32:10 
 * @author hjc 
 *
 */
public class InitSystem implements InitializingBean, ServletContextAware {
	
	@Autowired
	private LibraryService libraryService;
	@Override
	public void afterPropertiesSet() throws Exception {
		libraryService.loadLibraryToRedis();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("clouddbservice_authentication 系统初始化中....");
		try {
			//初始化进行的操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("clouddbservice_authentication 初始化完成！");
		
	}
	
	

}
