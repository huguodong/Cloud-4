package com.ssitcloud.entity;

/**
 * 统计查询模板详情
 *
 * <p>2017年2月10日 下午2:20:45  
 * @author hjc 
 *
 */
public class StatisticsConfigEntity {
	
	private Integer statistics_tpl_idx;//	模板ID
	private Integer statistics_tpl_type;//		"模板类型  1查询模板 2统计模板"
	
	private String statistics_tpl_value;//		模板配置值

	public Integer getStatistics_tpl_idx() {
		return statistics_tpl_idx;
	}

	public void setStatistics_tpl_idx(Integer statistics_tpl_idx) {
		this.statistics_tpl_idx = statistics_tpl_idx;
	}

	public Integer getStatistics_tpl_type() {
		return statistics_tpl_type;
	}

	public void setStatistics_tpl_type(Integer statistics_tpl_type) {
		this.statistics_tpl_type = statistics_tpl_type;
	}

	public String getStatistics_tpl_value() {
		return statistics_tpl_value;
	}

	public void setStatistics_tpl_value(String statistics_tpl_value) {
		this.statistics_tpl_value = statistics_tpl_value;
	}

	

}
