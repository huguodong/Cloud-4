package com.ssitcloud.entity;

/**
 * 维护卡实体类
 *
 * <p>2017年3月28日 下午5:33:22  
 * @author hjc 
 *
 */
public class MaintenanceCardEntity {
	
	private Integer maintenance_idx; //int(11) NOT NULL卡ID
	private String card_type; //varchar(2) NOT NULL卡类型 1普通维护卡 2读者证做为维护卡
	private Integer lib_idx; //int(11) NOT NULL图书馆idx
	private String card_id; //varchar(30) NOT NULL卡号
	private String card_pwd; //varchar(30) NOT NULL卡号
	private String opercmds; //varchar(1024) NOT NULL权限列表，半角逗号分隔
	
	
	
	
	public String getCard_pwd() {
		return card_pwd;
	}
	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}
	public Integer getMaintenance_idx() {
		return maintenance_idx;
	}
	public void setMaintenance_idx(Integer maintenance_idx) {
		this.maintenance_idx = maintenance_idx;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getOpercmds() {
		return opercmds;
	}
	public void setOpercmds(String opercmds) {
		this.opercmds = opercmds;
	}
	
	

}
