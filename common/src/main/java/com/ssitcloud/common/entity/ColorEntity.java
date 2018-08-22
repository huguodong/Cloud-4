package com.ssitcloud.common.entity;

public class ColorEntity {
	private String colorId;
	private String colorEN;
	private String colorCN;
	
	
	public ColorEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public ColorEntity(String colorId, String colorEN, String colorCN) {
		super();
		this.colorId = colorId;
		this.colorEN = colorEN;
		this.colorCN = colorCN;
	}

	public String getColorId() {
		return colorId;
	}
	public void setColorId(String colorId) {
		this.colorId = colorId;
	}
	public String getColorEN() {
		return colorEN;
	}
	public void setColorEN(String colorEN) {
		this.colorEN = colorEN;
	}
	public String getColorCN() {
		return colorCN;
	}
	public void setColorCN(String colorCN) {
		this.colorCN = colorCN;
	}
}
