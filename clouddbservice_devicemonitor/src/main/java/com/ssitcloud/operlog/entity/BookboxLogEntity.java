package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 书箱
 * 
 * @author Administrator
 * 
 */
@Document(collection = "bookbox_log")
public class BookboxLogEntity {
	@Id
	private String id;
	@Field(value = "opertime")
	private String opertime;
	@Field(value = "operator")
	private String operator;
	@Field(value = "opercmd")
	private String opercmd;
	@Field(value = "operresult")
	private String operresult;
	@Field(value = "LogisticsNo")
	private String logisticsNo;
	@Field(value = "LogisticsBin")
	private String logisticsBin;
	@Field(value = "Barcode")
	private String barcode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOpertime() {
		return opertime;
	}

	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOpercmd() {
		return opercmd;
	}

	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}

	public String getOperresult() {
		return operresult;
	}

	public void setOperresult(String operresult) {
		this.operresult = operresult;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getLogisticsBin() {
		return logisticsBin;
	}

	public void setLogisticsBin(String logisticsBin) {
		this.logisticsBin = logisticsBin;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

}
