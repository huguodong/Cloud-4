package com.ssitcloud.business.statistics.common.scheduled;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;

import com.ssitcloud.business.statistics.service.BookItemService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 馆藏数据保存到es中
 * es命名规则  图书馆ID_devID_BOOKITEM
 * 或者  图书馆ID_OTHER_BOOKITEM
 * <p>2017年8月14日 上午11:29:45  
 * @author hjc 
 *
 */
@JobHandler(value="scheduledBookItem")
@Component
public class BookItemScheduled  extends IJobHandler{

	

	@Resource
	private BookItemService bookItemService;
	
	
	/**
	 * 获取bookitem  biblios 等数据保存到es中
	 *
	 * <p>2017年8月14日 下午6:37:19 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行mixBookItem");
		Long start = System.currentTimeMillis();
		bookItemService.getAllBookItem();
		System.out.println("执行mixBookItem,耗时：" + (System.currentTimeMillis() - start) + "ms");
		return SUCCESS;
	}

}
