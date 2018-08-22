package com.ssitcloud.mobile.entity;

public class AcsQueryReservationEntity {
	private final String operation = "lib_querymybookinfo";
	private String card_no;

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getOperation() {
		return operation;
	}
	
	
}
