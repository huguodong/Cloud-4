package com.ssitcloud.devicelog.entity;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtStateDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -978817941265427169L;
	// 外设类型
	@Field(value = "logic_obj")
	@JsonProperty(value = "logic_obj")
	private String logicObj;
	// 外设状态
	@Field(value = "logic_state")
	@JsonProperty(value = "logic_state")
	private String logicState;

	public ExtStateDetail() {
	}

	public ExtStateDetail(String logicObj, String logicState) {
		this.logicState = logicState;
		this.logicObj = logicObj;
	}

	public String getLogicObj() {
		return logicObj;
	}

	public void setLogicObj(String logicObj) {
		this.logicObj = logicObj;
	}

	public String getLogicState() {
		return logicState;
	}

	public void setLogicState(String logicState) {
		this.logicState = logicState;
	}

}
