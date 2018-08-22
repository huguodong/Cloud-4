package com.ssitcloud.devmgmt.entity;

import java.security.Timestamp;
/**
 * 
 * @comment
 * `patch_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID号',
  `patch_version` varchar(20) NOT NULL COMMENT '版本号',
  `patch_desc` varchar(200) NOT NULL COMMENT '版本说明',
  `patch_type` varchar(50) DEFAULT NULL COMMENT '版本类型',
  `restrict_info` varchar(100) DEFAULT NULL COMMENT '约束说明',
  `patch_directory` varchar(200) NOT NULL COMMENT '客户端路径',
  `create_time` datetime NOT NULL COMMENT '创建时间',
 * @author gcy
 * @data 2016年5月11日
 */
public class PatchInfoEntity {
	private int patch_idx;
	private String patch_version;
	private String patch_desc;
	private String patch_type;
	private String restrict_info;
	private String patch_directory;
	private Timestamp create_time;
	

	public static final String ALL_RESTRICT="1";//全网
	public static final String LIB_RESTRICT="2";//馆限定
	public static final String DEV_RESTRICT="3";//设备限定

	
	public int getPatch_idx() {
		return patch_idx;
	}

	public void setPatch_idx(int patch_idx) {
		this.patch_idx = patch_idx;
	}

	public String getPatch_version() {
		return patch_version;
	}

	public void setPatch_version(String patch_version) {
		this.patch_version = patch_version;
	}

	public String getPatch_desc() {
		return patch_desc;
	}

	public void setPatch_desc(String patch_desc) {
		this.patch_desc = patch_desc;
	}

	public String getPatch_type() {
		return patch_type;
	}

	public void setPatch_type(String patch_type) {
		this.patch_type = patch_type;
	}

	public String getRestrict_info() {
		return restrict_info;
	}

	public void setRestrict_info(String restrict_info) {
		this.restrict_info = restrict_info;
	}

	public String getPatch_directory() {
		return patch_directory;
	}

	public void setPatch_directory(String patch_directory) {
		this.patch_directory = patch_directory;
	}

	public Timestamp getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}

}
