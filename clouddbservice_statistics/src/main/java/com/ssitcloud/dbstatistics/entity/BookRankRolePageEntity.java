package com.ssitcloud.dbstatistics.entity;

import com.ssitcloud.common.entity.DatagridPageEntity;

public class BookRankRolePageEntity extends DatagridPageEntity {
	private Integer role_idx;
	private String library_idx;
	private String role_content;
	private String remark;

	public Integer getRole_idx() {
		return role_idx;
	}

	public void setRole_idx(Integer role_idx) {
		this.role_idx = role_idx;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public String getRole_content() {
		return role_content;
	}

	public void setRole_content(String role_content) {
		this.role_content = role_content;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
