package com.ssitcloud.view.statistics.statisticsmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;




/**
 * 统计查询模板详情
 *
 * <p>2017年3月30日  
 * @author lqw 
 *
 */
public interface StatisticsConfigService {
	/**
	 * 统计查询模板详情StatisticsConfigEntity插入
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsConfigEntity
	 * @return
	 */
	ResultEntity insertStatisticsConfig(String req);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity修改
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsConfigEntity
	 * @return
	 */
	ResultEntity updateStatisticsConfig(String req);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity删除
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsConfigEntity
	 * @return
	 */
	ResultEntity deleteStatisticsConfig(String req);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity单个查询
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsConfigEntity
	 * @return
	 */
	ResultEntity queryOneStatisticsConfig(String req);
	
	/**
	 * 统计查询模板详情StatisticsConfigEntity多个查询
	 * <p>2017年3月30日  
     * @author lqw 
	 * @param statisticsConfigEntity
	 * @return
	 */
	ResultEntity queryStatisticsConfigs(String req);
}
