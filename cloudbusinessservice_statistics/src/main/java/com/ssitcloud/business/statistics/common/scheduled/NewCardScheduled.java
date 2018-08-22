package com.ssitcloud.business.statistics.common.scheduled;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import net.sf.ehcache.Cache;

import com.ssitcloud.business.statistics.service.NewCardService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

/**
 * 
 * 
 * <p>2017年4月1日 上午9:33:21  
 * @author hjc 
 *
 */

@JobHandler(value="scheduledNewCard")
@Component
public class NewCardScheduled  extends IJobHandler{

	
	
	@Resource
	private NewCardService newCardService;

	
	/**
	 * 办证数据保存到es 中
	 *
	 * <p>2017年4月1日 上午9:40:49 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行mixNewCard");
		long start = System.currentTimeMillis();
		newCardService.getAllNewCard();
		System.out.println("执行mixNewCard定时任务,耗时：" + (System.currentTimeMillis() - start ) + "ms");
		return SUCCESS;
	}


}
