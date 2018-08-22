package com.ssitcloud.devmgmt.entity;

/**
 * 
 * @comment
 * 
 * @author hwl
 * @data 2016年4月9日
 */
public class MetadataExtModelEntity {

	private Integer meta_ext_idx;

	private String ext_model;
	private String ext_model_desc;
	private String ext_model_version;
	private String ext_type;

	public Integer getMeta_ext_idx() {
		return meta_ext_idx;
	}

	public void setMeta_ext_idx(Integer meta_ext_idx) {
		this.meta_ext_idx = meta_ext_idx;
	}

	public String getExt_model() {
		return ext_model;
	}

	public void setExt_model(String ext_model) {
		this.ext_model = ext_model;
	}

	public String getExt_model_desc() {
		return ext_model_desc;
	}

	public void setExt_model_desc(String ext_model_desc) {
		this.ext_model_desc = ext_model_desc;
	}

	public String getExt_model_version() {
		return ext_model_version;
	}

	public void setExt_model_version(String ext_model_version) {
		this.ext_model_version = ext_model_version;
	}

	public String getExt_type() {
		return ext_type;
	}

	public void setExt_type(String ext_type) {
		this.ext_type = ext_type;
	}

}
