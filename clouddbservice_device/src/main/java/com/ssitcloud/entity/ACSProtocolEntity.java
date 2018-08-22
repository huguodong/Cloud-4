package com.ssitcloud.entity;

import java.sql.Timestamp;

/*********************************************************************
 * 
 *    1.模板 、配置 关联查询
 *    2.显示到页面，按原页面改进，修改配置（弹出页）
 *    3.
	protocol_idx	int(11) NOT NULL模板ID号或设备ID号
	device_tpl_type	int(11) NOT NULL模板区分 0模板 1数据
	library_idx	int(11) NOT NULL馆ID
	protocol_type	int(11) NOT NULL指令类型 1SIP2 2NCIP
	protocol_library_idx	int(11) NOT NULL指令字段描述，外关连metadata_protocol
	protocol_reqrule	varchar(500) NULL请求规则
	protocol_resprule	varchar(500) NULL响应规则
	protocol_description	varchar(1000) NULL描述
	operator_idx	int(11) NOT NULL操作员ID
	createtime	timestamp NOT NULL创建时间
	updatetime	timestamp NOT NULL更新时间
 * 	  @author lbh
 *********************************************************************/
public class ACSProtocolEntity{
	/* ID */
	private Integer protocol_idx;
	/* acs类型 */
	private Integer protocol_type;
	/* 模板类型 （不用） */
	private Integer device_tpl_type;
	/* 图书馆IDX */
	private Integer library_idx;
	/* protocol_show 外键  */
	private Integer protocol_library_idx;
	/* 请求规则 */
	private String protocol_reqrule;
	/* 响应规则 */
	private String protocol_resprule;
	/* 协议描述 */
	private String protocol_description;
	
	private Integer operator_idx;
	
	private Timestamp createtime;
	
	private Timestamp updatetime;
	
	public ACSProtocolEntity(Integer protocol_idx){
		this.protocol_idx=protocol_idx;
	}
	
	public ACSProtocolEntity(){}
	
	public Integer getProtocol_idx() {
		return protocol_idx;
	}

	public void setProtocol_idx(Integer protocol_idx) {
		this.protocol_idx = protocol_idx;
	}

	public Integer getProtocol_type() {
		return protocol_type;
	}

	public void setProtocol_type(Integer protocol_type) {
		this.protocol_type = protocol_type;
	}

	public Integer getDevice_tpl_type() {
		return device_tpl_type;
	}

	public void setDevice_tpl_type(Integer device_tpl_type) {
		this.device_tpl_type = device_tpl_type;
	}

	public Integer getProtocol_library_idx() {
		return protocol_library_idx;
	}

	public void setProtocol_library_idx(Integer protocol_library_idx) {
		this.protocol_library_idx = protocol_library_idx;
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

	public String getProtocol_description() {
		return protocol_description;
	}

	public void setProtocol_description(String protocol_description) {
		this.protocol_description = protocol_description;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
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

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	
	
}
