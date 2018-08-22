package com.ssitcloud.view.statistics.statisticsmgmt.service;

import com.ssitcloud.common.entity.ResultEntity;

/**
 * 从Elasticsearch获取查询与统计模板的数据
 * @author lqw  2017年4月8号
 *
 */
public interface ElasticsearchStatisticsService {
	/**
	 * 统计模板
	 * @param req
	 * @return
	 */
	ResultEntity statistics(String req);
	/**
	 * 查询模板
	 * @param req
	 * @return
	 */
	ResultEntity query(String req);
	
	/**
	 * 通过数据源获得其名下的分组
	 * author huanghuang
	 * 2017年4月10日 上午9:55:00
	 * @param indexTab 索引名
	 * @param gArr 组
	 * @return
	 */
	ResultEntity gtree(String req);
	
	/**
	 * 导出查询
	 * @param req
	 * @return
	 */
	ResultEntity exportSelect(String req);
	
	/**
	 * 获取es存在的图书馆
	 * @param req
	 * @return
	 */
	ResultEntity libArr(String req);
	/**
	 * 获取es存在的设备
	 * @param req
	 * @return
	 */
	ResultEntity devArr(String req);
}
