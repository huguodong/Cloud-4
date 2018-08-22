package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cardissue_log")
public class CardissueLogEntity {
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
	
	@Field(value = "errorcode")
	private String errorcode;
	
	@Field(value = "TransDate")
	private String TransDate;
	
	@Field(value = "createTime")
	private String createTime;

	@Field(value = "AuthCardno")
	private String AuthCardno;

	@Field(value = "AuthType")
	private String AuthType;

	@Field(value = "Cardtype")
	private String Cardtype;

//	@Field(value = "CardTypeName")
//	private String cardTypeName;

	@Field(value = "ExpireDate")
	private String ExpireDate;

	@Field(value = "PrivilegeFee")
	private double PrivilegeFee;

	@Field(value = "Name")
	private String Name;

	@Field(value = "Birth")
	private String Birth;

//	@Field(value = "Age")
//	private int age;

	@Field(value = "Gender")
	private String Gender;

	@Field(value = "EthnicGroup")
	private String EthnicGroup;

	@Field(value = "Address")
	private String Address;

//	@Field(value = "Email")
//	private String email;

	@Field(value = "Telephone")
	private String Telephone;

	@Field(value = "Mobile")
	private String Mobile;

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

//	public int getAge() {
//		return age;
//	}
//
//	public void setAge(int age) {
//		this.age = age;
//	}
//
//	public String getCardTypeName() {
//		return cardTypeName;
//	}
//
//	public void setCardTypeName(String cardTypeName) {
//		this.cardTypeName = cardTypeName;
//	}

}
