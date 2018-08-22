package com.ssitcloud.dao;

import java.util.List;

import com.ssitcloud.entity.StatisticsGroupTemplateEntity;

public interface StatisticsGroupTemplateDao {
	/**
	 * 根据模板idx查询统计模板名和统计模板组表
	 * 2017年3月10号 lqw
	 */
	public List<StatisticsGroupTemplateEntity> findById(StatisticsGroupTemplateEntity sgte);
	/**
	 * 查询统计模板名和统计模板组表
	 * 2017年3月10号 lqw
	 */
	public List<StatisticsGroupTemplateEntity> findAll(StatisticsGroupTemplateEntity sgte);

}
