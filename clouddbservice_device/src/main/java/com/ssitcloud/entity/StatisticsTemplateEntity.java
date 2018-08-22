package com.ssitcloud.entity;

/**
 * 统计查询模板配置
 *
 * <p>2017年2月10日 下午2:22:45  
 * @author hjc 
 *
 */
public class StatisticsTemplateEntity {
	private Integer statistics_tpl_idx;//模板IDX
	private String statistics_tpl_id;//模板ID
	private String statistics_tpl_desc;//模板名
	
	public Integer getStatistics_tpl_idx() {
		return statistics_tpl_idx;
	}
	public void setStatistics_tpl_idx(Integer statistics_tpl_idx) {
		this.statistics_tpl_idx = statistics_tpl_idx;
	}
	
	public String getStatistics_tpl_id() {
		return statistics_tpl_id;
	}
	public void setStatistics_tpl_id(String statistics_tpl_id) {
		this.statistics_tpl_id = statistics_tpl_id;
	}
	public String getStatistics_tpl_desc() {
		return statistics_tpl_desc;
	}
	public void setStatistics_tpl_desc(String statistics_tpl_desc) {
		this.statistics_tpl_desc = statistics_tpl_desc;
	}
	
}
