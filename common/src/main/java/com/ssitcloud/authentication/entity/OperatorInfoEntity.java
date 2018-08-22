package com.ssitcloud.authentication.entity;

/**
 * <p>2016年4月5日 上午11:54:28
 * @author hjc
 *
 */
public class OperatorInfoEntity {
	/** 操作员表自增ID */
	private Integer operator_idx;
	/** 元数据表自增ID */
	private Integer infotype_idx;
	/** 值 */
	private String info_value;
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public Integer getInfotype_idx() {
		return infotype_idx;
	}
	public void setInfotype_idx(Integer infotype_idx) {
		this.infotype_idx = infotype_idx;
	}
	public String getInfo_value() {
		return info_value;
	}
	public void setInfo_value(String info_value) {
		this.info_value = info_value;
	}
	
	
}
