package com.ssitcloud.datasync.entity;

public class ProtocolInfo {
	// 协议模块id
	private Integer protocol_tpl_idx;
	// 协议类型
	private Integer protocol_type;
	// 协议模板描述
	private String protocol_tpl_desc;
	// 图书馆idx
	private String library_idx;
	// 设备模板类型
	private String device_tpl_type;
	// 协议执行id
	private Integer protocol_library_idx;
	// 协议描述
	private String protocol_description;
	// 协议指令请求
	private String protocol_reqrule;
	// 协议指令响应
	private String protocol_resprule;
	// 操作idx
	private Integer operator_idx;
	// 创建时间
	private String createtime;
	// 更新时间
	private String updatetime;
	// 协议指令简写
	private String protocol_show;
	// 协议指令名
	private String protocol_field_name;

	public Integer getProtocol_tpl_idx() {
		return protocol_tpl_idx;
	}

	public void setProtocol_tpl_idx(Integer protocol_tpl_idx) {
		this.protocol_tpl_idx = protocol_tpl_idx;
	}

	public Integer getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}

	public String getProtocol_tpl_desc() {
		return protocol_tpl_desc;
	}

	public Integer getProtocol_library_idx() {
		return protocol_library_idx;
	}

	public void setProtocol_library_idx(Integer protocol_library_idx) {
		this.protocol_library_idx = protocol_library_idx;
	}

	public void setProtocol_tpl_desc(String protocol_tpl_desc) {
		this.protocol_tpl_desc = protocol_tpl_desc;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public String getDevice_tpl_type() {
		return device_tpl_type;
	}

	public void setDevice_tpl_type(String device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}

	public String getProtocol_description() {
		return protocol_description;
	}

	public void setProtocol_description(String protocol_description) {
		this.protocol_description = protocol_description;
	}

	public String getProtocol_reqrule() {
		return protocol_reqrule;
	}

	public void setProtocol_reqrule(String protocol_reqrule) {
		this.protocol_reqrule = protocol_reqrule;
	}

	public String getProtocol_resprule() {
		return protocol_resprule;
	}

	public void setProtocol_resprule(String protocol_resprule) {
		this.protocol_resprule = protocol_resprule;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getProtocol_show() {
		return protocol_show;
	}

	public void setProtocol_show(String protocol_show) {
		this.protocol_show = protocol_show;
	}

	public String getProtocol_field_name() {
		return protocol_field_name;
	}

	public void setProtocol_field_name(String protocol_field_name) {
		this.protocol_field_name = protocol_field_name;
	}

	@Override
	public String toString() {
		return "ProtocolInfo [protocol_tpl_idx=" + protocol_tpl_idx + ", protocol_type=" + protocol_type + ", protocol_tpl_desc=" + protocol_tpl_desc + ", library_idx=" + library_idx
				+ ", device_tpl_type=" + device_tpl_type + ", protocol_library_idx=" + protocol_library_idx + ", protocol_reqrule=" + protocol_reqrule + ", protocol_resprule=" + protocol_resprule
				+ ", operator_idx=" + operator_idx + ", createtime=" + createtime + ", updatetime=" + updatetime + ", protocol_show=" + protocol_show + ", protocol_field_name=" + protocol_field_name
				+ "]";
	}

}
