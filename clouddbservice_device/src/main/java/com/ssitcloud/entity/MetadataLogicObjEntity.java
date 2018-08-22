package com.ssitcloud.entity;

/**
 * 
 * @comment 逻辑对象元数据表metadata_logic_obj
 * `meta_log_obj_idx` int(11) NOT NULL AUTO_INCREMENT,
 * `logic_obj` varchar(100) NOT NULL COMMENT '逻辑部件ID',
 * `logic_obj_desc` varchar(100) NOT NULL COMMENT '逻辑部件描述',
 * @author hwl
 * @data 2016年4月11日
 */
public class MetadataLogicObjEntity {

	private int meta_log_obj_idx;
	private String logic_obj;
	private String logic_obj_desc;
	public int getMeta_log_obj_idx() {
		return meta_log_obj_idx;
	}
	public void setMeta_log_obj_idx(int meta_log_obj_idx) {
		this.meta_log_obj_idx = meta_log_obj_idx;
	}
	public String getLogic_obj() {
		return logic_obj;
	}
	public void setLogic_obj(String logic_obj) {
		this.logic_obj = logic_obj;
	}
	public String getLogic_obj_desc() {
		return logic_obj_desc;
	}
	public void setLogic_obj_desc(String logic_obj_desc) {
		this.logic_obj_desc = logic_obj_desc;
	}
	
	
}
