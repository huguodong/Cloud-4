package com.ssitcloud.view.statistics.deepCopy;

import java.io.Serializable;

/**
 * 
 * author huanghuang
 * 2017年3月31日 上午9:52:16
 */
public class StatisticsConfigEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2641370337866117767L;
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
