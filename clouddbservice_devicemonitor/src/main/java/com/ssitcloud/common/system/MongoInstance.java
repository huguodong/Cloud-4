package com.ssitcloud.common.system;


/**
 * 
 * @package: com.ketu.selfservice.common.system
 * @classFile: MongoInstance
 * @author: liubh
 * @createTime:
 * @description: 数据库实例信息 包括IP、端口、用户名、密码、超级用户所在库、要操作的库（要操作的库=libId+设备名）
 */
public class MongoInstance {
	private String host;
	private int port;
	private String user;
	private String pwd;
	private String operDatabase;

	public MongoInstance() {
	}

	public MongoInstance(String host, int port, String user,
			String pwd, String operDatabase) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
		this.operDatabase = operDatabase;
	}

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

	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getOperDatabase() {
		return operDatabase;
	}

	public void setOperDatabase(String operDatabase) {
		this.operDatabase = operDatabase;
	}

}
