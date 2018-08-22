package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "finance_log")
public class FinanceLogEntity {
	@Id
	private String id;
	@Field(value = "opertime")
	private String opertime;// 交易时间

	@Field(value = "operator")
	private String operator;// 读者卡号

	@Field(value = "opercmd")
	private String opercmd;

	@Field(value = "operresult")
	private String operresult;// 1成功　０失败2普通收款

	@Field(value = "errorcode")
	private String errorcode;
	
	@Field(value = "TransDate")
	private String TransDate;
	
	@Field(value = "Cardtype")
	private String Cardtype;// 读者证类型

	@Field(value = "AuthCardno")
	private String AuthCardno;

	@Field(value = "AuthType")
	private String AuthType;
	
	@Field(value = "Deposit")
	private String Deposit;
	
	@Field(value = "PrivilegeFee")
	private String PrivilegeFee;
	
	@Field(value = "createTime")
	private String createTime;

	@Field(value = "UniCardno")
	private String UniCardno;// 扣款卡号

	@Field(value = "Name")
	private String Name;// 姓名

	@Field(value = "Purpose")
	private String Purpose;// 存款用途

	@Field(value = "Payway")
	private String Payway;// 付款/退款方式 C 现金支付 Y 预付款扣除 I IC卡扣除 E 取消欠款 N 设置欠款 Q 取款

	@Field(value = "Money")
	private double Money;// 交易金额付款、取款为正，扣款为负

	@Field(value = "FiscalTranID")
	private String FiscalTranID;// 服务器记录的财经记录号（成功划账）

	@Field(value = "Barcode")
	private String Barcode;

	@Field(value = "SumDay")
	private String SumDay;// 天数/倍数

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

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getTransDate() {
		return TransDate;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getCardtype() {
		return Cardtype;
	}

	public void setCardtype(String cardtype) {
		Cardtype = cardtype;
	}

	public String getAuthCardno() {
		return AuthCardno;
	}

	public void setAuthCardno(String authCardno) {
		AuthCardno = authCardno;
	}

	public String getAuthType() {
		return AuthType;
	}

	public void setAuthType(String authType) {
		AuthType = authType;
	}

	public String getUniCardno() {
		return UniCardno;
	}

	public void setUniCardno(String uniCardno) {
		UniCardno = uniCardno;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getPurpose() {
		return Purpose;
	}

	public void setPurpose(String purpose) {
		Purpose = purpose;
	}

	public String getPayway() {
		return Payway;
	}

	public void setPayway(String payway) {
		Payway = payway;
	}

	public double getMoney() {
		return Money;
	}

	public void setMoney(double money) {
		Money = money;
	}

	public String getFiscalTranID() {
		return FiscalTranID;
	}

	public void setFiscalTranID(String fiscalTranID) {
		FiscalTranID = fiscalTranID;
	}

	public String getBarcode() {
		return Barcode;
	}

	public void setBarcode(String barcode) {
		Barcode = barcode;
	}

	public String getSumDay() {
		return SumDay;
	}

	public void setSumDay(String sumDay) {
		SumDay = sumDay;
	}

	public String getDeposit() {
		return Deposit;
	}

	public void setDeposit(String deposit) {
		Deposit = deposit;
	}

	public String getPrivilegeFee() {
		return PrivilegeFee;
	}

	public void setPrivilegeFee(String privilegeFee) {
		PrivilegeFee = privilegeFee;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
