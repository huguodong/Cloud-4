package com.ssitcloud.node.entity;

public class FlowInfo {
	/** 主键id */
	private int flow_idx;
	/** 节点id */
	private String node_id;
	/** 发送时间 */
	private long send_time;
	/** 队列长度 */
	private int queue_length;
	/** 处理时长 */
	private String process_info;
	/** 等待时长 */
	private String wait_info;

	public int getFlow_idx() {
		return flow_idx;
	}

	public void setFlow_idx(int flow_idx) {
		this.flow_idx = flow_idx;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}

	public int getQueue_length() {
		return queue_length;
	}

	public void setQueue_length(int queue_length) {
		this.queue_length = queue_length;
	}

	public String getProcess_info() {
		return process_info;
	}

	public void setProcess_info(String process_info) {
		this.process_info = process_info;
	}

	public String getWait_info() {
		return wait_info;
	}

	public void setWait_info(String wait_info) {
		this.wait_info = wait_info;
	}

	@Override
	public String toString() {
		return "FlowInfo [flow_idx=" + flow_idx + ", node_id=" + node_id
				+ ", send_time=" + send_time + ", queue_length=" + queue_length
				+ ", process_info=" + process_info + ", wait_info=" + wait_info
				+ "]";
	}
}
