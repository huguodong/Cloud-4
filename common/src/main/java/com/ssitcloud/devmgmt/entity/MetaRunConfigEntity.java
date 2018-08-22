package com.ssitcloud.devmgmt.entity;

import java.security.Timestamp;

import com.ssitcloud.common.entity.DatagridPageEntity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月8日
 */
public class MetaRunConfigEntity extends DatagridPageEntity {

	/**
	 * 
	 */
	
	private Integer meta_run_cfg_idx;
	private String run_conf_type;
	private String run_conf_type_desc;
	private Timestamp updatetime;

	public MetaRunConfigEntity() {
	}

	public MetaRunConfigEntity(Integer id) {
		this.meta_run_cfg_idx = id;
	}

	public Integer getMeta_run_cfg_idx() {
		return meta_run_cfg_idx;
	}

	public void setMeta_run_cfg_idx(Integer meta_run_cfg_idx) {
		this.meta_run_cfg_idx = meta_run_cfg_idx;
	}

	public String getRun_conf_type() {
		return run_conf_type;
	}

	public void setRun_conf_type(String run_conf_type) {
		this.run_conf_type = run_conf_type;
	}

	public String getRun_conf_type_desc() {
		return run_conf_type_desc;
	}

	public void setRun_conf_type_desc(String run_conf_type_desc) {
		this.run_conf_type_desc = run_conf_type_desc;
	}

	public Timestamp getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Timestamp updatetime) {
		this.updatetime = updatetime;
	}

}
