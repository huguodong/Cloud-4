package com.ssitcloud.business.statistics.common.scheduled;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;

import com.ssitcloud.business.statistics.service.FinanceService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 
 *
 * <p>2017年4月1日 上午9:33:15  
 * @author hjc 
 *
 */

@JobHandler(value="scheduledFinance")
@Component
public class FinanceScheduled  extends IJobHandler{


	
	@Resource
	private FinanceService financeService;

	
	/**
	 * 财经数据保存到es中
	 *
	 * <p>2017年4月1日 上午9:40:49 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行mixFinance");
		long start = System.currentTimeMillis();
		financeService.getAllFinance();
		System.out.println("执行ReaderCardScheduled定时任务,耗时：" + (System.currentTimeMillis() - start ) + "ms");
		return SUCCESS;
	}

}
