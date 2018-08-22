package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 *
 * <p>2017年5月15日 
 * @author lqw 
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class FinanceLogEntity {
	
	@JestId
	private String id;
	
	@JsonProperty("opertime")
	private String opertime;// 交易时间

	@JsonProperty("operator")
	private String operator;// 读者卡号

	@JsonProperty("opercmd")
	private String opercmd;

	@JsonProperty("operresult")
	private String operresult;// 1成功　０失败2普通收款

	@JsonProperty("errorcode")
	private String errorcode;
	
	@JsonProperty("TransDate")
	private String TransDate;
	
	@JsonProperty("Cardtype")
	private String Cardtype;// 读者证类型

	@JsonProperty("AuthCardno")
	private String AuthCardno;

	@JsonProperty("AuthType")
	private String AuthType;
	
	@JsonProperty("Deposit")
	private String Deposit;
	
	@JsonProperty("PrivilegeFee")
	private String PrivilegeFee;
	
	@JsonProperty("createTime")
	private String createTime;

	@JsonProperty("UniCardno")
	private String UniCardno;// 扣款卡号

	@JsonProperty("Name")
	private String Name;// 姓名

	@JsonProperty("Purpose")
	private String Purpose;// 存款用途

	@JsonProperty("Payway")
	private String Payway;// 付款/退款方式 C 现金支付 Y 预付款扣除 I IC卡扣除 E 取消欠款 N 设置欠款 Q 取款

	@JsonProperty("Money")
	private double Money;// 交易金额付款、取款为正，扣款为负

	@JsonProperty("FiscalTranID")
	private String FiscalTranID;// 服务器记录的财经记录号（成功划账）

	@JsonProperty("Barcode")
	private String Barcode;

	@JsonProperty("SumDay")
	private String SumDay;// 天数/倍数
	
	
	
	/** 图书馆相关参数 */
	private String library_idx;
	private String lib_name;
	private String lib_id;
	
	/**  设备相关的参数 **/
	private String device_idx;
	private String device_id;
	private String device_name;
	private String device_type;
	private String device_type_desc;
	private String device_type_mark;
	private String areaCode;
	
	
	
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getDevice_idx() {
		return device_idx;
	}

	public void setDevice_idx(String device_idx) {
		this.device_idx = device_idx;
	}

	public String getLibrary_idx() {
		return library_idx;
	}

	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}

	public String getLib_name() {
		return lib_name;
	}

	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}

	public String getLib_id() {
		return lib_id;
	}

	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_type_desc() {
		return device_type_desc;
	}

	public void setDevice_type_desc(String device_type_desc) {
		this.device_type_desc = device_type_desc;
	}

	public String getDevice_type_mark() {
		return device_type_mark;
	}

	public void setDevice_type_mark(String device_type_mark) {
		this.device_type_mark = device_type_mark;
	}

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

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	
	
}
