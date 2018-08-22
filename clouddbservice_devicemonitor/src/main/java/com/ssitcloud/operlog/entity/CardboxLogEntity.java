package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 卡箱
 * 
 * @author Administrator
 * 
 */
@Document(collection = "cardbox_log")
public class CardboxLogEntity {
	@Id
	private String id;
	@Field(value = "opertime")
	@Indexed(direction = IndexDirection.DESCENDING)
	private String opertime;
	@Field(value = "operator")
	private String operator;
	@Field(value = "operresult")
	private String operresult;
	@Field(value = "opercmd")
	private String opercmd;
	@Field(value = "LogisticsNo")
	private String logisticsNo;
	@Field(value = "PersellCardno")
	private String persellCardno;
	@Field(value = "Cardcount")
	private String cardcount;

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

	public String getOperresult() {
		return operresult;
	}

	public void setOperresult(String operresult) {
		this.operresult = operresult;
	}

	public String getOpercmd() {
		return opercmd;
	}

	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getPersellCardno() {
		return persellCardno;
	}

	public void setPersellCardno(String persellCardno) {
		this.persellCardno = persellCardno;
	}

	public String getCardcount() {
		return cardcount;
	}

	public void setCardcount(String cardcount) {
		this.cardcount = cardcount;
	}

}
