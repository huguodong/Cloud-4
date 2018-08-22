package com.ssitcloud.devmgmt.entity;

public class DevStatusCode {
	private String ext_logic_obj;
	private String ext_model_code;
	private String ext_model_info;
	private Integer ext_code_type;
	
	public String getExt_logic_obj() {
		return ext_logic_obj;
	}
	public void setExt_logic_obj(String ext_logic_obj) {
		this.ext_logic_obj = ext_logic_obj;
	}
	public String getExt_model_code() {
		return ext_model_code;
	}
	public void setExt_model_code(String ext_model_code) {
		this.ext_model_code = ext_model_code;
	}
	public String getExt_model_info() {
		return ext_model_info;
	}
	public void setExt_model_info(String ext_model_info) {
		this.ext_model_info = ext_model_info;
	}
	public Integer getExt_code_type() {
		return ext_code_type;
	}
	public void setExt_code_type(Integer ext_code_type) {
		this.ext_code_type = ext_code_type;
	}
	@Override
	public String toString() {
		return "DeviceStatus2 [ext_logic_obj=" + ext_logic_obj
				+ ", ext_model_code=" + ext_model_code + ", ext_model_info="
				+ ext_model_info + ", ext_code_type=" + ext_code_type + "]";
	}
}
