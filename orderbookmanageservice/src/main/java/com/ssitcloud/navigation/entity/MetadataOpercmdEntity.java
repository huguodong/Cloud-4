package com.ssitcloud.navigation.entity;
/**
 * 
 * @comment 操作指令元数据表。表名：metadata_opercmd
 * 
 * @author hwl
 * @data 2016年4月7日
 */
public class MetadataOpercmdEntity {
	
	// 自增id
	private Integer meta_opercmd_idx;
	// 操作指令
	private String opercmd;
	//操作指令描述
	private String opercmd_desc;
	private String opercmd_typeinfo;
	private String operbusinesstype;
	//指令url
	private String opercmd_url;

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

	public String getOpercmd_typeinfo() {
		return opercmd_typeinfo;
	}

	public void setOpercmd_typeinfo(String opercmd_typeinfo) {
		this.opercmd_typeinfo = opercmd_typeinfo;
	}

	public String getOperbusinesstype() {
		return operbusinesstype;
	}

	public void setOperbusinesstype(String operbusinesstype) {
		this.operbusinesstype = operbusinesstype;
	}
	
	

}
