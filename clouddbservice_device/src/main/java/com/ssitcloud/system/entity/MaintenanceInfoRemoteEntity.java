package com.ssitcloud.system.entity;


/**
 * 维护卡  同步数据
 * @author lbh
 *
 */
public class MaintenanceInfoRemoteEntity {
	
	/****
	 * 维护卡数据
	 * 
	 * 20170408 修改， 注释掉maintenance_card_id 
	 * 新增card_type、card_id、card_pwd三个字段， 维护卡下发需要的数据
	 *****/
	private String card_type;//new 
	private String card_id;//new  
	private String card_pwd;//new 
//	private String maintenance_card_id; //old
	private String opercmds;
	
	
	public String getOpercmds() {
		return opercmds;
	}
	public void setOpercmds(String opercmds) {
		this.opercmds = opercmds;
	}
//	public String getMaintenance_card_id() {
//		return maintenance_card_id;
//	}
//	public void setMaintenance_card_id(String maintenance_card_id) {
//		this.maintenance_card_id = maintenance_card_id;
//	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCard_pwd() {
		return card_pwd;
	}
	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}
	
	
	
	
}
