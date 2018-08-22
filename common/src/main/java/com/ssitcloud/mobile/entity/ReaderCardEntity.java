package com.ssitcloud.mobile.entity;

import java.util.Date;

public class ReaderCardEntity {
	
	private Integer reader_idx;
	private Integer lib_idx;
	/**读者证*/
	private String card_no;
	/**实际读者证*/
	private String actual_card_no;
	/**读者卡类型*/
	private String card_type;
	/**读者证密码*/
	private String card_password;
	/**是否有效*/
	private Integer valid;
	/**过期时间*/
	private Date expire_date;
	/**读者姓名*/
	private String reader_name;
	/**身份证*/
	private String id_card;
	/**性别*/
	private String gender;
	/**地址*/
	private String address;
	/**出生日期*/
	private Date birth;
	/**手机号*/
	private String mobile;
	/**邮箱*/
	private String email;
	/**最大借阅数*/
	private Integer allown_loancount;
	/**剩余可借数*/
	private Integer surplus_count;
	/**预付金*/
	private double advance_charge;
	/**押金*/
	private double deposit;
	/**欠款*/
	private double arrearage;
	private Date create_time;
	private Date update_time;
	
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
	public String getCard_no() {
		return card_no;
	}
	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}
	public String getActual_card_no() {
		return actual_card_no;
	}
	public void setActual_card_no(String actual_card_no) {
		this.actual_card_no = actual_card_no;
	}
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	
	public String getCard_password() {
		return card_password;
	}
	public void setCard_password(String card_password) {
		this.card_password = card_password;
	}
	public Integer getValid() {
		return valid;
	}
	public void setValid(Integer valid) {
		this.valid = valid;
	}
	public Date getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(Date expire_date) {
		this.expire_date = expire_date;
	}
	
	public String getReader_name() {
		return reader_name;
	}
	public void setReader_name(String reader_name) {
		this.reader_name = reader_name;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getAllown_loancount() {
		return allown_loancount;
	}
	public void setAllown_loancount(Integer allown_loancount) {
		this.allown_loancount = allown_loancount;
	}
	
	public Integer getSurplus_count() {
		return surplus_count;
	}
	public void setSurplus_count(Integer surplus_count) {
		this.surplus_count = surplus_count;
	}
	public double getAdvance_charge() {
		return advance_charge;
	}
	public void setAdvance_charge(double advance_charge) {
		this.advance_charge = advance_charge;
	}
	public double getDeposit() {
		return deposit;
	}
	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}
	public double getArrearage() {
		return arrearage;
	}
	public void setArrearage(double arrearage) {
		this.arrearage = arrearage;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
}
