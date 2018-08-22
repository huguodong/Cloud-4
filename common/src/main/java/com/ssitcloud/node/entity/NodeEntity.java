package com.ssitcloud.node.entity;

/***********************************************************************
 * Module:  Node.java
 * Author:  dell
 * Purpose: Defines the Class Node
 ***********************************************************************/

/** 节点 */
public class NodeEntity {
	/** 主键ID */
	private Integer node_idx;
	/** 节点业务类型 */
	private String node_model;
	/** 节点类型id */
	private Integer node_type_idx;
	/** 节点编号 */
	private String node_id;
	/** 节点名称 */
	private String node_name;
	/** 节点属性 */
	private String node_attributes;
	/** 容器id */
	private Integer container_idx;
	/** 服务图书馆列表id */
	private String library_idxs;
	/** 关联节点列表id */
	private String node_relations;
	/**节点是否开启 1:开启 0：关闭*/
	private Integer enable;

	public Integer getNode_idx() {
		return node_idx;
	}

	public void setNode_idx(Integer node_idx) {
		this.node_idx = node_idx;
	}

	public String getNode_model() {
		return node_model;
	}

	public void setNode_model(String node_model) {
		this.node_model = node_model;
	}

	public Integer getNode_type_idx() {
		return node_type_idx;
	}

	public void setNode_type_idx(Integer node_type_idx) {
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

	@Override
	public String toString() {
		return "NodeEntity [node_idx=" + node_idx + ", node_model=" + node_model + ", node_type_idx=" + node_type_idx + ", node_id=" + node_id + ", node_name=" + node_name + ", node_attributes="
				+ node_attributes + ", container_idx=" + container_idx + ", library_idxs=" + library_idxs + "node_relations=" + node_relations + "]";
	}

}