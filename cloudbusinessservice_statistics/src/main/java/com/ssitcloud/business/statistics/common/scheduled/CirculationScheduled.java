package com.ssitcloud.business.statistics.common.scheduled;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.ssitcloud.business.statistics.service.CirculationService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;

import net.sf.ehcache.Cache;

/**
 * 
 *
 * <p>2017年4月1日 上午9:32:48  
 * @author hjc 
 *
 */
@JobHandler(value="scheduledCirculation")
@Component
public class CirculationScheduled  extends IJobHandler{

	
	@Resource
	private CirculationService circulationService;
	
	/**
	 * 将各类流通数据处理之后保存到es中
	 *
	 * <p>2017年4月1日 上午9:40:49 
	 * <p>create by hjc
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception{
		System.out.println("执行mixCirculation");
		Long start = System.currentTimeMillis();
		circulationService.getAllCirculation();
		System.out.println("执行mixCirculation,耗时：" + (System.currentTimeMillis() - start) + "ms");
		return SUCCESS;
	}


}
