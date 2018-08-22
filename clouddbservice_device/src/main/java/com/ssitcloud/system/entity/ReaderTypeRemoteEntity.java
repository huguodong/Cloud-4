package com.ssitcloud.system.entity;

public class ReaderTypeRemoteEntity {
	
	private Integer type_idx;
	private String type_id;
	private String type_name;
	private Integer type_deposit;
	private Integer card_fee;
	private Integer verification_fee;

	public Integer getType_idx() {
		return type_idx;
	}
	public void setType_idx(Integer type_idx) {
		this.type_idx = type_idx;
	}
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public Integer getType_deposit() {
		return type_deposit;
	}
	public void setType_deposit(Integer type_deposit) {
		this.type_deposit = type_deposit;
	}
	public Integer getCard_fee() {
		return card_fee;
	}
	public void setCard_fee(Integer card_fee) {
		this.card_fee = card_fee;
	}
	public Integer getVerification_fee() {
		return verification_fee;
	}
	public void setVerification_fee(Integer verification_fee) {
		this.verification_fee = verification_fee;
	}
	
}
