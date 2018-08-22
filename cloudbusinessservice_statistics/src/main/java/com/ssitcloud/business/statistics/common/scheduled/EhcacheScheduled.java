package com.ssitcloud.business.statistics.common.scheduled;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.service.LibraryService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import net.sf.ehcache.Cache;

/**
 * 装载数据到ehcache，设备数据以及图书馆数据
 *
 * <p>2017年3月21日 下午1:46:36  
 * @author hjc 
 *
 */
@JobHandler(value="scheduledEhcache")
@Component
public class EhcacheScheduled  extends IJobHandler{
	

	
	@Resource
	private LibraryService libraryService;
	
	
	/**
	 * 从ssitcloud_statistics表中获取数据，然后完善读者信息，设备信息，图书馆信息，将这些信息保存到es中
	 *
	 * <p>2017年4月1日 上午9:41:37 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行cacheLibraryAndDevice");
		Long start = System.currentTimeMillis();
		libraryService.getAllLibraryAndDeviceList();
		System.out.println("执行cacheLibraryAndDevice,耗时：" + (System.currentTimeMillis() - start) + "ms");
		return SUCCESS;
	}
	

}
