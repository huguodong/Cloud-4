package com.ssitcloud.node.entity;

import java.io.Serializable;

public class NodeMonitor implements Serializable {
	private static final long serialVersionUID = 8064082584407043825L;
	/** 主键ID */
	private int node_idx;
	/** 节点业务类型 */
	private String node_model;
	/** 节点类型id */
	private int node_type_idx;
	/** 节点类型 */
	private String node_type_name;
	/** 节点编号 */
	private String node_id;
	/** 节点名称 */
	private String node_name;
	/** 节点属性 */
	private String node_attributes;
	/** 容器id */
	private int container_idx;
	/** 容器名称 */
	private String container_name;
	/** 服务图书馆列表id */
	private String library_idxs;
	/** 节点状态 */
	private String node_status;
	/** 关联节点列表id */
	private String node_relations;
	/** 创建时间 */
	private String send_time;
	private String queue_length;
	private String process_info;
	private String wait_info;

	public int getNode_idx() {
		return node_idx;
	}

	public void setNode_idx(int node_idx) {
		this.node_idx = node_idx;
	}

	public String getNode_model() {
		return node_model;
	}

	public void setNode_model(String node_model) {
		this.node_model = node_model;
	}

	public int getNode_type_idx() {
		return node_type_idx;
	}

	public void setNode_type_idx(int node_type_idx) {
		this.node_type_idx = node_type_idx;
	}

	public String getNode_type_name() {
		return node_type_name;
	}

	public void setNode_type_name(String node_type_name) {
		this.node_type_name = node_type_name;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public String getNode_name() {
		return node_name;
	}

	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}

	public String getNode_attributes() {
		return node_attributes;
	}

	public void setNode_attributes(String node_attributes) {
		this.node_attributes = node_attributes;
	}

	public int getContainer_idx() {
		return container_idx;
	}

	public void setContainer_idx(int container_idx) {
		this.container_idx = container_idx;
	}

	public String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(String container_name) {
		this.container_name = container_name;
	}

	public String getLibrary_idxs() {
		return library_idxs;
	}

	public void setLibrary_idxs(String library_idxs) {
		this.library_idxs = library_idxs;
	}

	public String getNode_status() {
		return node_status;
	}

	public void setNode_status(String node_status) {
		this.node_status = node_status;
	}

	public String getNode_relations() {
		return node_relations;
	}

	public void setNode_relations(String node_relations) {
		this.node_relations = node_relations;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	

	public String getQueue_length() {
		return queue_length;
	}

	public void setQueue_length(String queue_length) {
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
		return "NodeMonitor [node_idx=" + node_idx + ", node_model=" + node_model + ", node_type_idx=" + node_type_idx + ", node_type_name=" + node_type_name + ", node_id=" + node_id + ", node_name="
				+ node_name + ", node_attributes=" + node_attributes + ", container_idx=" + container_idx + ", container_name=" + container_name + ", library_idxs=" + library_idxs + ", node_status="
				+ node_status + ", node_relations=" + node_relations + ", send_time=" + send_time + "]";
	}

}
