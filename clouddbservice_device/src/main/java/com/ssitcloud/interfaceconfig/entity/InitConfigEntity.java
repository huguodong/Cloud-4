package com.ssitcloud.interfaceconfig.entity;


public class InitConfigEntity {
	private String name; // 配置项名称
	private String fieldset; // 分组名
	private String[] value; // 配置项json内键值

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getValue() {
		return value;
	}

	public void setValue(String[] value) {
		this.value = value;
	}

	public String getFieldset() {
		return fieldset;
	}

	public void setFieldset(String fieldset) {
		this.fieldset = fieldset;
	}

}
