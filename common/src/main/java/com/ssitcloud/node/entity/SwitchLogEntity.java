package com.ssitcloud.node.entity;

import java.util.Date;

public class SwitchLogEntity {
	/** 主键id */
	private int log_idx;
	/** 创建时间 */
	private Date create_time;
	/** 当前节点 */
	private String current_node;
	/** 目标节点 */
	private String target_node;
	/** 操作类型 */
	private String switch_type;
	/** 操作结果 */
	private int result;
	/** 备注 */
	private String remark;

	public int getLog_idx() {
		return log_idx;
	}

	public void setLog_idx(int log_idx) {
		this.log_idx = log_idx;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getCurrent_node() {
		return current_node;
	}

	public void setCurrent_node(String current_node) {
		this.current_node = current_node;
	}

	public String getTarget_node() {
		return target_node;
	}

	public void setTarget_node(String target_node) {
		this.target_node = target_node;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getSwitch_type() {
		return switch_type;
	}

	public void setSwitch_type(String switch_type) {
		this.switch_type = switch_type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SwitchLogEntity [log_idx=" + log_idx + ", create_time=" + create_time + ", current_node=" + current_node + ", target_node=" + target_node + ", switch_type=" + switch_type
				+ ", result=" + result + ", remark=" + remark + "]";
	}
	
}
