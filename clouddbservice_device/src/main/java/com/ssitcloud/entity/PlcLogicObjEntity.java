package com.ssitcloud.entity;
/**
 * 
 * @author hwl
 *`plc_logic_obj_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT 'PLC状态IDX',
  `plc_logic_obj` varchar(50) DEFAULT NULL COMMENT '逻辑部件名',
  `plc_logic_obj_desc` varchar(100) DEFAULT NULL COMMENT '逻辑部件描述',
 */
public class PlcLogicObjEntity {
	private Integer plc_logic_obj_idx;
	private String plc_logic_obj;
	private String plc_logic_obj_desc;
	
	
	
	public Integer getPlc_logic_obj_idx() {
		return plc_logic_obj_idx;
	}
	public void setPlc_logic_obj_idx(Integer plc_logic_obj_idx) {
		this.plc_logic_obj_idx = plc_logic_obj_idx;
	}
	public String getPlc_logic_obj() {
		return plc_logic_obj;
	}
	public void setPlc_logic_obj(String plc_logic_obj) {
		this.plc_logic_obj = plc_logic_obj;
	}
	public String getPlc_logic_obj_desc() {
		return plc_logic_obj_desc;
	}
	public void setPlc_logic_obj_desc(String plc_logic_obj_desc) {
		this.plc_logic_obj_desc = plc_logic_obj_desc;
	}
	
	
}
