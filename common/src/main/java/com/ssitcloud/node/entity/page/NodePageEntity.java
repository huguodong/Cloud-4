package com.ssitcloud.node.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 
 * @comment 节点表，存储节点信息。表名： node
 * 
 * @author xiebf
 * @data 2016年9月5日
 */
public class NodePageEntity extends DatagridPageEntity {
	private static final long serialVersionUID = 4694963223753677158L;
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
	/**是否启用*/
	private Integer enable;
	public NodePageEntity() {
	}

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

	public Integer getContainer_idx() {
		return container_idx;
	}

	public void setContainer_idx(Integer container_idx) {
		this.container_idx = container_idx;
	}

	public String getLibrary_idxs() {
		return library_idxs;
	}

	public void setLibrary_idxs(String library_idxs) {
		this.library_idxs = library_idxs;
	}

	public String getNode_type_name() {
		return node_type_name;
	}

	public void setNode_type_name(String node_type_name) {
		this.node_type_name = node_type_name;
	}

	public String getContainer_name() {
		return container_name;
	}

	public void setContainer_name(String container_name) {
		this.container_name = container_name;
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

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	
	public String toString() {
		return "NodePageEntity [node_idx=" + node_idx + ", node_model=" + node_model + ", node_type_idx=" + node_type_idx + ", node_type_name=" + node_type_name + ", node_id=" + node_id
				+ ", node_name=" + node_name + ", node_attributes=" + node_attributes + ", container_idx=" + container_idx + ", container_name=" + container_name + ", library_idxs=" + library_idxs
				+ ", node_status=" + node_status + ", node_relations=" + node_relations + "]";
	}

	
}
