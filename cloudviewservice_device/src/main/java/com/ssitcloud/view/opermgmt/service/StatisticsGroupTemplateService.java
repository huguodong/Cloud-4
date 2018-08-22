package com.ssitcloud.view.opermgmt.service;

import com.ssitcloud.common.entity.ResultEntity;



public interface StatisticsGroupTemplateService {
	/**
	 * 根据模板idx查询统计模板名和统计模板组表
	 * 2017年3月10号 lqw
	 */
	public ResultEntity findById(String req);
	/**
	 * 查询统计模板名和统计模板组表
	 * 2017年3月10号 lqw
	 */
	public ResultEntity findAll(String req);


}
