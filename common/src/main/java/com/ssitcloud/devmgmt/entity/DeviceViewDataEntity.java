package com.ssitcloud.devmgmt.entity;

public class DeviceViewDataEntity {
	/***
	 * id  -----id
	 * data -----data
	 */
	
	private Integer id;
	private String operation;//json数据
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
	

}
