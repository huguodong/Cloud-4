package com.ssitcloud.devicelog.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 设备状态 发生变化的数据会存入此表
 * 
 * 2016年3月18日
 * 
 * @param <T>
 * 
 * @param <T>
 * @param <T>
 */
@Document(collection = "state_log")
public class StateLogEntity<T> {
	@Id
	private String stateLID;
	/**
	 * tableName:哪个表发生变化就是哪个表名
	 */
	@Field(value = "table_name")
	private String tableName;
	/**
	 * 原始ID
	 */
	@Field(value = "old_id")
	private String oldID;
	/**
	 * 原始状态
	 */
	@Field(value = "old_state")
	private List<T> oldState;
	/**
	 * 接口获取的新的状态
	 */
	@Field(value = "new_state")
	private List<T> newState;
	// YYYYMMDDHHDDMM
	/**
	 * 插入表的时间
	 */
	@Field(value = "create_time")
	@Indexed(direction = IndexDirection.DESCENDING)
	private String createTime;

	public StateLogEntity() {
	}

	public StateLogEntity(String tableName, String oldID, List<T> oldState,
			List<T> newState, String createTime) {
		this.tableName = tableName;
		this.oldID = oldID;
		this.oldState = oldState;
		this.newState = newState;
		this.createTime = createTime;
	}

	public String getStateLID() {
		return stateLID;
	}

	public void setStateLID(String stateLID) {
		this.stateLID = stateLID;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getOldID() {
		return oldID;
	}

	public void setOldID(String oldID) {
		this.oldID = oldID;
	}

	public List<T> getOldState() {
		return oldState;
	}

	public void setOldState(List<T> oldState) {
		this.oldState = oldState;
	}

	public List<T> getNewState() {
		return newState;
	}

	public void setNewState(List<T> newState) {
		this.newState = newState;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
