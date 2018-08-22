package com.ssitcloud.devmgmt.entity;

public class DeviceServiceQueue {
	
	private int queue_idx;
	/**队列id*/
	private String queue_id;
	/**服务idx*/
	private int device_service_idx;
	/**队列类型*/
	private String queue_type;
	/**队列名称*/
	private String queue_name;
	/**队列ip*/
	private String queue_ip;
	/**队列端口*/
	private String queue_port;
	/**虚拟主机*/
	private String queue_virtual_host;
	/**交换机*/
	private String queue_exchange;
	/**路由*/
	private String queue_route;
	/**登陆名称*/
	private String queue_login_name;
	/**登陆密码*/
	private String queue_login_pwd;
	/**服务ID*/;
	private String device_service_id;
	
	
	public int getQueue_idx() {
		return queue_idx;
	}
	public void setQueue_idx(int queue_idx) {
		this.queue_idx = queue_idx;
	}
	public int getDevice_service_idx() {
		return device_service_idx;
	}
	public void setDevice_service_idx(int device_service_idx) {
		this.device_service_idx = device_service_idx;
	}
	public String getQueue_type() {
		return queue_type;
	}
	public void setQueue_type(String queue_type) {
		this.queue_type = queue_type;
	}
	public String getQueue_name() {
		return queue_name;
	}
	public void setQueue_name(String queue_name) {
		this.queue_name = queue_name;
	}
	public String getQueue_ip() {
		return queue_ip;
	}
	public void setQueue_ip(String queue_ip) {
		this.queue_ip = queue_ip;
	}
	public String getQueue_port() {
		return queue_port;
	}
	public void setQueue_port(String queue_port) {
		this.queue_port = queue_port;
	}
	public String getQueue_virtual_host() {
		return queue_virtual_host;
	}
	public void setQueue_virtual_host(String queue_virtual_host) {
		this.queue_virtual_host = queue_virtual_host;
	}
	public String getQueue_exchange() {
		return queue_exchange;
	}
	public void setQueue_exchange(String queue_exchange) {
		this.queue_exchange = queue_exchange;
	}
	public String getQueue_route() {
		return queue_route;
	}
	public void setQueue_route(String queue_route) {
		this.queue_route = queue_route;
	}
	public String getQueue_login_name() {
		return queue_login_name;
	}
	public void setQueue_login_name(String queue_login_name) {
		this.queue_login_name = queue_login_name;
	}
	public String getQueue_login_pwd() {
		return queue_login_pwd;
	}
	public void setQueue_login_pwd(String queue_login_pwd) {
		this.queue_login_pwd = queue_login_pwd;
	}
	public String getQueue_id() {
		return queue_id;
	}
	public void setQueue_id(String queue_id) {
		this.queue_id = queue_id;
	}
	public String getDevice_service_id() {
		return device_service_id;
	}
	public void setDevice_service_id(String device_service_id) {
		this.device_service_id = device_service_id;
	}
	
	
}
