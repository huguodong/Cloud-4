package com.ssitcloud.node.entity.page;

import java.sql.Timestamp;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class NodeInterfacePageEntity extends DatagridPageEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer interface_idx;
	private String request_url;//请求url
	private String request_id;//请求id
	private String from_node;//所属节点
	private String to_node;//访问节点
	private String protocol;//协议
	private String ip;
	private String port;
	private Timestamp create_time;
	private Timestamp update_time;
	private Integer operator;
	private String save_path;//容器保存
	private String node_mode;//节点类型
	public Integer getInterface_idx() {
		return interface_idx;
	}
	public void setInterface_idx(Integer interface_idx) {
		this.interface_idx = interface_idx;
	}
	public String getRequest_url() {
		return request_url;
	}
	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getFrom_node() {
		return from_node;
	}
	public void setFrom_node(String from_node) {
		this.from_node = from_node;
	}
	public String getTo_node() {
		return to_node;
	}
	public void setTo_node(String to_node) {
		this.to_node = to_node;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	public Integer getOperator() {
		return operator;
	}
	public void setOperator(Integer operator) {
		this.operator = operator;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getSave_path() {
		return save_path;
	}
	public void setSave_path(String save_path) {
		this.save_path = save_path;
	}
	public String getNode_mode() {
		return node_mode;
	}
	public void setNode_mode(String node_mode) {
		this.node_mode = node_mode;
	}
	
	
	
	

}
