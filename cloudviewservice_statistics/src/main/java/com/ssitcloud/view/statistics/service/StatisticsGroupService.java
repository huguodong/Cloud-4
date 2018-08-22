package com.ssitcloud.view.statistics.service;

import com.ssitcloud.common.entity.ResultEntity;


public interface StatisticsGroupService {
	/**
	 * 新增
	 * author huanghuang
	 * 2017年4月28日 上午10:57:08
	 * @param req
	 * @return
	 */
	ResultEntity increaseStatisticsGroup(String req);
	/**
	 * 修改
	 * author huanghuang
	 * 2017年4月28日 上午10:57:19
	 * @param req
	 * @return
	 */
	ResultEntity modifyStatisticsGroup(String req);
	/**
	 * 删除
	 * author huanghuang
	 * 2017年4月28日 上午10:57:34
	 * @param req
	 * @return
	 */
	ResultEntity removeStatisticsGroup(String req);
	/**
	 * 查询
	 * author huanghuang
	 * 2017年4月28日 上午10:57:44
	 * @param req
	 * @return
	 */
	ResultEntity querysGroupByPageParam(String req);

}
