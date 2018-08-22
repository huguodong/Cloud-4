package com.ssitcloud.view.statistics.deepCopy;

import java.io.Serializable;

/**
 * 
 * author huanghuang
 * 2017年3月31日 上午9:52:10
 */
public class StatisticsTemplateEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1707909309824672537L;
	private Integer statistics_tpl_idx;//模板IDX
	private String statistics_tpl_id;//模板ID
	private String statistics_tpl_desc;//模板名
	
	private transient StatisticsConfigEntity statisticsConfigEntity;
	
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
	public StatisticsConfigEntity getStatisticsConfigEntity() {
		return statisticsConfigEntity;
	}
	public void setStatisticsConfigEntity(
			StatisticsConfigEntity statisticsConfigEntity) {
		this.statisticsConfigEntity = statisticsConfigEntity;
	}
	
	
}
