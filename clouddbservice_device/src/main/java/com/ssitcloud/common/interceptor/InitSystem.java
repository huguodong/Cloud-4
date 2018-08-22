package com.ssitcloud.common.interceptor;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.ServletContextAware;

import com.ssitcloud.service.DeviceService;
import com.ssitcloud.service.MainfieldService;
import com.ssitcloud.service.MetadataDeviceTypeService;
import com.ssitcloud.service.MetadataOpercmdTableService;

/**
 * 启动服务的时候调用的方法
 *
 * <p>2016年3月22日 下午5:32:10 
 * @author hjc 
 *
 */
public class InitSystem implements InitializingBean, ServletContextAware {
	
	@Resource
	private MetadataOpercmdTableService cmdTableService;
	@Resource
	private MainfieldService fieldService;
	@Resource
	private DeviceService deviceService;
	@Resource
	private MetadataDeviceTypeService deviceTypeService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try{
			cmdTableService.loadCMDTableToRedis();
			fieldService.loadMainFieldToRedis();
			deviceService.loadDeviceToRedis();
			deviceTypeService.loadDeviceTypeToRedis();
		}catch(Exception e){
			System.out.println("基础数据初始化失败");
			e.printStackTrace();
		}
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
