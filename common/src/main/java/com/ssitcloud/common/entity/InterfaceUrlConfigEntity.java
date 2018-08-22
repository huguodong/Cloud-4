package com.ssitcloud.common.entity;

/**
 * 节点配置实体类
 * 
 * @author yeyalin 2017-08-21
 */
public class InterfaceUrlConfigEntity {

	/**
	 * from:源节点
	 */
	private String from_node;
	/**
	 * 目标节点
	 */
	private String to_node;
	/**
	 * 目标节点IP
	 */
	private String ip;
	/**
	 * 目标节点端口
	 */
	private String port;
	/**
	 * 目标节点urlId
	 */
	private String request_id;
	/**
	 * 目标节点接口
	 */
	private String request_url;
	
	/**
	 * 协议类型：http/https
	 */
	private String protocol ;

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

	public String getRequest_id() {
		return request_id;
	}

	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}

	public String getRequest_url() {
		return request_url;
	}

	public void setRequest_url(String request_url) {
		this.request_url = request_url;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public String toString() {
		return "InterfaceUrlConfigEntity [from_node=" + from_node
				+ ", to_node=" + to_node + ", ip=" + ip + ", port=" + port
				+ ", request_id=" + request_id + ", request_url=" + request_url
				+ "]";
	}

}
