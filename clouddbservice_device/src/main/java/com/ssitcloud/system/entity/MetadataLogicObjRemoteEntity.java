package com.ssitcloud.system.entity;

/**
 * 
 * @comment 逻辑对象元数据表metadata_logic_obj
 * `meta_log_obj_idx` int(11) NOT NULL AUTO_INCREMENT,
 * `logic_obj` varchar(100) NOT NULL COMMENT '逻辑部件ID',
 * `logic_obj_desc` varchar(100) NOT NULL COMMENT '逻辑部件描述',
	数据同步接口专用
 */
public class MetadataLogicObjRemoteEntity {

	private String logic_obj;
	private String logic_obj_desc;
	
	
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
