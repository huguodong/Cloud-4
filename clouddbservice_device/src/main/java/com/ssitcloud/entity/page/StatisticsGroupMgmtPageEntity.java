package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 模板组分页实体
 * author huanghuang
 * 2017年2月20日 下午1:34:31
 */
public class StatisticsGroupMgmtPageEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer statistics_group_idx;//		模板组IDX
	private String statistics_group_id;//	模板组ID
	private String statistics_group_name;//	模板组名
	private String statistics_group_desc;//		模板组描述
	private String statistics_idx_str;//		模板组下的模板
	
	public Integer getStatistics_group_idx() {
		return statistics_group_idx;
	}
	public void setStatistics_group_idx(Integer statistics_group_idx) {
		this.statistics_group_idx = statistics_group_idx;
	}
	public String getStatistics_group_id() {
		return statistics_group_id;
	}
	public void setStatistics_group_id(String statistics_group_id) {
		this.statistics_group_id = statistics_group_id;
	}
	public String getStatistics_group_name() {
		return statistics_group_name;
	}
	public void setStatistics_group_name(String statistics_group_name) {
		this.statistics_group_name = statistics_group_name;
	}
	public String getStatistics_group_desc() {
		return statistics_group_desc;
	}
	public void setStatistics_group_desc(String statistics_group_desc) {
		this.statistics_group_desc = statistics_group_desc;
	}
	public String getStatistics_idx_str() {
		return statistics_idx_str;
	}
	public void setStatistics_idx_str(String statistics_idx_str) {
		this.statistics_idx_str = statistics_idx_str;
	}
	
	
	
}
