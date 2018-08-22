package com.ssitcloud.entity;

public class MaintenanceCardEntityF {
	
	private String card_type; //varchar(2) NOT NULL卡类型 1普通维护卡 2读者证做为维护卡
	private String card_id; //varchar(30) NOT NULL卡号
	private String card_pwd; //varchar(30) NOT NULL卡号
	private String opercmds; //varchar(1024) NOT NULL权限列表，半角逗号分隔
	
	public String getCard_pwd() {
		return card_pwd;
	}
	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}
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
	public String getOpercmds() {
		return opercmds;
	}
	public void setOpercmds(String opercmds) {
		this.opercmds = opercmds;
	}

}
