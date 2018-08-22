package com.ssitcloud.entity.page;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 权限分组页面 分页实体
 * @author lbh
 *
 */
public class OperCmdMgmtPageEntity extends DatagridPageEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 *  sg.`service_group_idx`,
	 *  sg.`service_group_id`,
	 *  sg.`library_idx`,
	 *  sg.`service_group_name`,
	 *  sg.`service_group_desc`,
		meta_opercmd_idx_str,
		opercmd_desc_str
	 */
	private Integer service_group_idx;
	
	private String service_group_id;
	private Integer library_idx;
	
	private String service_group_name;
	private String service_group_desc;
	private String meta_opercmd_idx_str;
	private String opercmd_str;
	private String opercmd_desc_str;
	private Integer service_group_type;
	private Integer version_stamp;

	public Integer getService_group_idx() {
		return service_group_idx;
	}

	public void setService_group_idx(Integer service_group_idx) {
		this.service_group_idx = service_group_idx;
	}

	public String getService_group_id() {
		return service_group_id;
	}

	public void setService_group_id(String service_group_id) {
		this.service_group_id = service_group_id;
	}

	public Integer getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(Integer library_idx) {
		this.library_idx = library_idx;
	}

	public String getService_group_name() {
		return service_group_name;
	}

	public void setService_group_name(String service_group_name) {
		this.service_group_name = service_group_name;
	}

	public String getService_group_desc() {
		return service_group_desc;
	}

	public void setService_group_desc(String service_group_desc) {
		this.service_group_desc = service_group_desc;
	}

	public String getMeta_opercmd_idx_str() {
		return meta_opercmd_idx_str;
	}

	public void setMeta_opercmd_idx_str(String meta_opercmd_idx_str) {
		this.meta_opercmd_idx_str = meta_opercmd_idx_str;
	}

	public String getOpercmd_desc_str() {
		return opercmd_desc_str;
	}

	public void setOpercmd_desc_str(String opercmd_desc_str) {
		this.opercmd_desc_str = opercmd_desc_str;
	}

	public String getOpercmd_str() {
		return opercmd_str;
	}

	public void setOpercmd_str(String opercmd_str) {
		this.opercmd_str = opercmd_str;
	}

	public Integer getService_group_type() {
		return service_group_type;
	}

	public void setService_group_type(Integer service_group_type) {
		this.service_group_type = service_group_type;
	}

	public Integer getVersion_stamp() {
		return version_stamp;
	}

	public void setVersion_stamp(Integer version_stamp) {
		this.version_stamp = version_stamp;
	}

}
