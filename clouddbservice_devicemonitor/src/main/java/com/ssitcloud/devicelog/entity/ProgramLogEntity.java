package com.ssitcloud.devicelog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "program_log")
public class ProgramLogEntity {
	@Id
	private String proLID;
	@Field(value = "proName")
	private String proName;

	@Field(value = "oldVersion")
	private String oldVersion;

	@Field(value = "newVersion")
	private String newVersion;

	@Field(value = "createTime")
	private String createTime;// YYYYMMDDHHMMSS

	@Field(value = "updateTime")
	private String updateTime;// YYYYMMDDHHMMSS

	public String getProLID() {
		return proLID;
	}

	public void setProLID(String proLID) {
		this.proLID = proLID;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getOldVersion() {
		return oldVersion;
	}

	public void setOldVersion(String oldVersion) {
		this.oldVersion = oldVersion;
	}

	public String getNewVersion() {
		return newVersion;
	}

	public void setNewVersion(String newVersion) {
		this.newVersion = newVersion;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
