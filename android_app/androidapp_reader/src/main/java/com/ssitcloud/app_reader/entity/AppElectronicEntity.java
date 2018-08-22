package com.ssitcloud.app_reader.entity;

import java.sql.Timestamp;
/**
 * app端电子凭证消息类
 * @author LXP
 * @version 创建时间：2017年3月31日 上午9:44:57
 */
public class AppElectronicEntity {
	private Integer electronic_idx;//id
	private Timestamp control_time;
	private Integer state;
	private String libraryName;
	private String title;//消息标题
	private String conetent;//消息内容
	public Integer getElectronic_idx() {
		return electronic_idx;
	}
	public void setElectronic_idx(Integer electronic_idx) {
		this.electronic_idx = electronic_idx;
	}
	public Timestamp getControl_time() {
		return control_time;
	}
	public void setControl_time(Timestamp control_time) {
		this.control_time = control_time;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getConetent() {
		return conetent;
	}
	public void setConetent(String conetent) {
		this.conetent = conetent;
	}
	public String getLibraryName() {
		return libraryName;
	}
	public void setLibraryName(String libraryName) {
		this.libraryName = libraryName;
	}
	
}
