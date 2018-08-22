package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 *
 * <p>2017年5月12日 
 * @author lqw 
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class CardissueLogEntity {
	
	@JestId
	private String id;

	@JsonProperty("opertime")
	private String opertime;

	@JsonProperty("operator")
	private String operator;

	@JsonProperty("opercmd")
	private String opercmd;

	@JsonProperty("operresult")
	private String operresult;
	
	@JsonProperty("errorcode")
	private String errorcode;
	
	@JsonProperty("TransDate")
	private String TransDate;
	
	@JsonProperty("createTime")
	private String createTime;

	@JsonProperty("AuthCardno")
	private String AuthCardno;

	@JsonProperty("AuthType")
	private String AuthType;

	@JsonProperty("Cardtype")
	private String Cardtype;

//	@JsonProperty("CardTypeName")
//	private String cardTypeName;

	@JsonProperty("ExpireDate")
	private String ExpireDate;

	@JsonProperty("PrivilegeFee")
	private double PrivilegeFee;

	@JsonProperty("Name")
	private String Name;

	@JsonProperty("Birth")
	private String Birth;

//	@JsonProperty("Age")
//	private int age;

	@JsonProperty("Gender")
	private String Gender;

	@JsonProperty("EthnicGroup")
	private String EthnicGroup;

	@JsonProperty("Address")
	private String Address;

//	@JsonProperty("Email")
//	private String email;

	@JsonProperty("Telephone")
	private String Telephone;

	@JsonProperty("Mobile")
	private String Mobile;
	
	
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getCardtype() {
		return Cardtype;
	}

	public void setCardtype(String cardtype) {
		Cardtype = cardtype;
	}

	public String getExpireDate() {
		return ExpireDate;
	}

	public void setExpireDate(String expireDate) {
		ExpireDate = expireDate;
	}

	public double getPrivilegeFee() {
		return PrivilegeFee;
	}

	public void setPrivilegeFee(double privilegeFee) {
		PrivilegeFee = privilegeFee;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getBirth() {
		return Birth;
	}

	public void setBirth(String birth) {
		Birth = birth;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getEthnicGroup() {
		return EthnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		EthnicGroup = ethnicGroup;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getTelephone() {
		return Telephone;
	}

	public void setTelephone(String telephone) {
		Telephone = telephone;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	
	

	
}
