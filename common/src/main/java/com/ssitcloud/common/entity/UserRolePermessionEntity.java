package com.ssitcloud.common.entity;

/**
 * 用户相关权限
 * @package: com.ssitcloud.common.entity
 * @classFile: UserRolePermessionEntity
 * @author: liuBh
 * @description: TODO
 */
public class UserRolePermessionEntity {
	//操作员 idx
	private Integer operator_idx;
	//图书馆 idx
	private Integer library_idx;
	//业务组 idx
	private Integer service_group_idx;
	//业务组 名字
	private String service_group_name;
	//业务命令字典 idx
	private Integer meta_opercmd_idx;
	//业务命令
	private String opercmd;
	//业务类型（菜单）
	private String operbusinesstype;
	//业务操作描述
	private String opercmd_desc;
	//资源URL
	private String opercmd_url;
	
	private Integer opercmd_typeinfo;
	
	public UserRolePermessionEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public UserRolePermessionEntity(Integer operator_idx) {
		super();
		this.operator_idx = operator_idx;
	}

	public UserRolePermessionEntity(Integer operator_idx, Integer library_idx,
			Integer service_group_idx, String service_group_name,
			Integer meta_opercmd_idx, String opercmd, String operbusinesstype,
			String opercmd_desc, String opercmd_url) {
		super();
		this.operator_idx = operator_idx;
		this.library_idx = library_idx;
		this.service_group_idx = service_group_idx;
		this.service_group_name = service_group_name;
		this.meta_opercmd_idx = meta_opercmd_idx;
		this.opercmd = opercmd;
		this.operbusinesstype = operbusinesstype;
		this.opercmd_desc = opercmd_desc;
		this.opercmd_url = opercmd_url;
	}
	
	public Integer getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getService_group_idx() {
		return service_group_idx;
	}
	public void setService_group_idx(Integer service_group_idx) {
		this.service_group_idx = service_group_idx;
	}
	public String getService_group_name() {
		return service_group_name;
	}
	public void setService_group_name(String service_group_name) {
		this.service_group_name = service_group_name;
	}
	public Integer getMeta_opercmd_idx() {
		return meta_opercmd_idx;
	}
	public void setMeta_opercmd_idx(Integer meta_opercmd_idx) {
		this.meta_opercmd_idx = meta_opercmd_idx;
	}
	public String getOpercmd() {
		return opercmd;
	}
	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}
	public String getOperbusinesstype() {
		return operbusinesstype;
	}
	public void setOperbusinesstype(String operbusinesstype) {
		this.operbusinesstype = operbusinesstype;
	}
	public String getOpercmd_desc() {
		return opercmd_desc;
	}
	public void setOpercmd_desc(String opercmd_desc) {
		this.opercmd_desc = opercmd_desc;
	}
	public String getOpercmd_url() {
		return opercmd_url;
	}
	public void setOpercmd_url(String opercmd_url) {
		this.opercmd_url = opercmd_url;
	}
	public Integer getOperator_idx() {
		return operator_idx;
	}
	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}
	public Integer getOpercmd_typeinfo() {
		return opercmd_typeinfo;
	}
	public void setOpercmd_typeinfo(Integer opercmd_typeinfo) {
		this.opercmd_typeinfo = opercmd_typeinfo;
	}
	
}
