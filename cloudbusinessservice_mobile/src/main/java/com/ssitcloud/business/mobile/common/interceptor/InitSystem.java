package com.ssitcloud.business.mobile.common.interceptor;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ServletContextAware;

import com.ssitcloud.business.mobile.service.LibraryCacheServiceI;
import com.ssitcloud.business.mobile.service.MetadataOpercmdCacheServiceI;

/**
 * 启动服务的时候调用的方法
 * 
 * <p>
 * 2016年3月22日 下午5:32:10
 * 
 * @author hjc
 * 
 */
public class InitSystem implements InitializingBean, ServletContextAware {
	@Autowired
	private LibraryCacheServiceI libraryCacheService;
	
	@Autowired
	private MetadataOpercmdCacheServiceI metadataOpercmdCacheService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		libraryCacheService.update();
		metadataOpercmdCacheService.update();
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		System.out.println("cloudbusinessservice_mobile 系统初始化中....");
		try {
			// 初始化进行的操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("cloudbusinessservice_mobile 初始化完成！");

	}

}
