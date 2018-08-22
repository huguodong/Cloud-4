package com.ssitcloud.entity;

/**
 * 操作员组与模板组关联表
 *
 * <p>2017年2月10日 下午2:18:52  
 * @author hjc 
 *
 */
public class RelOperatorStatisticsGroupEntity {
	private Integer rel_oper_statistics_idx;//	
	private Integer operator_group_idx;//		操作员组ID
	private Integer statistics_group_idx;//		模板组ID
	public Integer getRel_oper_statistics_idx() {
		return rel_oper_statistics_idx;
	}
	public void setRel_oper_statistics_idx(Integer rel_oper_statistics_idx) {
		this.rel_oper_statistics_idx = rel_oper_statistics_idx;
	}
	public Integer getOperator_group_idx() {
		return operator_group_idx;
	}
	public void setOperator_group_idx(Integer operator_group_idx) {
		this.operator_group_idx = operator_group_idx;
	}
	public Integer getStatistics_group_idx() {
		return statistics_group_idx;
	}
	public void setStatistics_group_idx(Integer statistics_group_idx) {
		this.statistics_group_idx = statistics_group_idx;
	}
	
	

}
