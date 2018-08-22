package com.ssitcloud.common.entity;

/**
 * ACS配置
 * @author LXP
 * @version 创建时间：2017年3月2日 下午2:46:16
 */
public class ACSConfigEntity {
	private String host;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String toString() {
		return "host==>"+host+" port==>"+port;
	}
}
