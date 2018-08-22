package com.ssitcloud.entity;

import java.sql.Timestamp;

/**
 * metadata_library_selfcheck_protocol
 * 
 * @package: com.ssitcloud.entity
 * @classFile: SelfCheckProtocolEntity
 * @author: liuBh
 * @description: TODO
 */
public class SelfCheckProtocolEntity {
	
	/**
	  * protocol_idx		int(11) NOT NULL模板ID号或设备ID号
		device_tpl_type		int(11) NOT NULL模板区分 0模板 1数据
		library_idx			int(11) NOT NULL馆ID
		protocol_type		int(11) NOT NULL指令类型 1SIP2 2NCIP
		protocol_show		varchar(50) NOT NULL指令代码 63、64等
		protocol_library_idx	int(11) NOT NULL指令字段描述，外关连library_selfcheck_protocol_config
		protocol_start		int(11) NULL指令起始位置
		protocol_end		int(11) NULL指令结束位置
		protocol_code		varchar(50) NULL指令标识符
		protocol_defalut	varchar(50) NULL默认值
		operator_idx		int(11) NOT NULL操作员ID
		createtime			timestamp NOT NULL创建时间
		updatetime			timestamp NOT NULL更新时间
	 */
	 
	private Integer protocol_idx;
	private Integer device_tpl_type;
	private Integer library_idx;
	private Integer protocol_type;
	private String protocol_show;
	private Integer protocol_library_idx;
	private Integer protocol_start;
	private Integer protocol_end;
	private String protocol_code;
	private String protocol_defalut;
	private Integer operator_idx;
	private Timestamp createtime;
	private Timestamp updatetime;
	
	
	 
	public SelfCheckProtocolEntity() {
	}
	public SelfCheckProtocolEntity(Integer protocol_idx) {
		this.protocol_idx = protocol_idx;
	}
	public Integer getProtocol_idx() {
		return protocol_idx;
	}
	public void setProtocol_idx(Integer protocol_idx) {
		this.protocol_idx = protocol_idx;
	}
	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}
	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getProtocol_type() {
		return protocol_type;
	}
	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}

	public Integer getProtocol_library_idx() {
		return protocol_library_idx;
	}
	public void setProtocol_library_idx(Integer protocol_library_idx) {
		this.protocol_library_idx = protocol_library_idx;
	}
	public Integer getProtocol_start() {
		return protocol_start;
	}
	public void setProtocol_start(Integer protocol_start) {
		this.protocol_start = protocol_start;
	}
	public Integer getProtocol_end() {
		return protocol_end;
	}
	public void setProtocol_end(Integer protocol_end) {
		this.protocol_end = protocol_end;
	}
	
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public String getProtocol_show() {
		return protocol_show;
	}
	public void setProtocol_show(String protocol_show) {
		this.protocol_show = protocol_show;
	}
	public String getProtocol_code() {
		return protocol_code;
	}
	public void setProtocol_code(String protocol_code) {
		this.protocol_code = protocol_code;
	}
	public String getProtocol_defalut() {
		return protocol_defalut;
	}
	public void setProtocol_defalut(String protocol_defalut) {
		this.protocol_defalut = protocol_defalut;
	}
	public Timestamp getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}
	public Timestamp getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

	
	
}
