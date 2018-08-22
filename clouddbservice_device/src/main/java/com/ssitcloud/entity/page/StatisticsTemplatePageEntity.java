package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class StatisticsTemplatePageEntity extends DatagridPageEntity {
	private static final long serialVersionUID = 1L;
	private Integer statistics_tpl_idx;//模板IDX
	private String statistics_tpl_id;//模板ID
	private String statistics_tpl_desc;//模板名
	private Integer statistics_tpl_type;//"模板类型  1查询模板 2统计模板"
	private String statistics_tpl_value;//模板配置值
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
