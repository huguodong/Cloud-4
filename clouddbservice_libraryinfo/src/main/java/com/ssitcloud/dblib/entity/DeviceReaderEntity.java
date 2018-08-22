package com.ssitcloud.dblib.entity;

public class DeviceReaderEntity {
	
	private Integer reader_idx;//int(11) NOT NULL读者记录号
	private Integer lib_idx;//int(11) NOT NULL图书馆记录号
	private String authcardno;//varchar(30) NULL读者证号
	private String authtype;//varchar(20) NULL认证类型
	private String cardtype;//varchar(10) NULL读者流通类型
	private String expiredate;//varchar(20) NULL有效期
	private double privilegefee;//double NULL预付款
	private String name;//varchar(30) NULL姓名
	private String birth;//varchar(10) NULL出生日期
	private String gender;//varchar(5) NULL性别 M男 F女
	private String ethnicgroup;//varchar(20) NULL民族
	private String address;//varchar(50) NULL家庭住址
	
	
	
	public Integer getReader_idx() {
		return reader_idx;
	}
	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getAuthcardno() {
		return authcardno;
	}
	public void setAuthcardno(String authcardno) {
		this.authcardno = authcardno;
	}
	public String getAuthtype() {
		return authtype;
	}
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public double getPrivilegefee() {
		return privilegefee;
	}
	public void setPrivilegefee(double privilegefee) {
		this.privilegefee = privilegefee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnicgroup() {
		return ethnicgroup;
	}
	public void setEthnicgroup(String ethnicgroup) {
		this.ethnicgroup = ethnicgroup;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
	

}
