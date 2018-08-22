package com.ssitcloud.database.entity;

import java.util.ArrayList;
import java.util.List;

public class Server implements java.io.Serializable {

	private Integer id = null;
	private String host = null;
	private Integer port = null;
	private String user = null;
	private String password = null;
	private String description = null;
	private List<MysqlDatabase> dbs = new ArrayList<>();
	private List<MongoDatabaseEntity> mdbs = new ArrayList<>();
	private String type = null;

	public Server() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MysqlDatabase> getDbs() {
		return dbs;
	}

	public void setDbs(List<MysqlDatabase> dbs) {
		this.dbs = dbs;
	}

	public List<MongoDatabaseEntity> getMdbs() {
		return mdbs;
	}

	public void setMdbs(List<MongoDatabaseEntity> mdbs) {
		this.mdbs = mdbs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return user + "@" + host + ":" + port.intValue();
	}

}