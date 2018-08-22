package com.ssitcloud.entity;

/**
 * 统计模板与模板组对应关系
 *
 * <p>2017年2月10日 下午2:19:35  
 * @author hjc 
 *
 */
public class RelStatisticsGroupEntity {
	private Integer rel_statistics_idx;//		
	private Integer statistics_group_idx;//		模板组ID
	private Integer statistics_idx;//	模板ID
	
	public Integer getRel_statistics_idx() {
		return rel_statistics_idx;
	}
	public void setRel_statistics_idx(Integer rel_statistics_idx) {
		this.rel_statistics_idx = rel_statistics_idx;
	}
	public Integer getStatistics_group_idx() {
		return statistics_group_idx;
	}
	public void setStatistics_group_idx(Integer statistics_group_idx) {
		this.statistics_group_idx = statistics_group_idx;
	}
	public Integer getStatistics_idx() {
		return statistics_idx;
	}
	public void setStatistics_idx(Integer statistics_idx) {
		this.statistics_idx = statistics_idx;
	}

}
