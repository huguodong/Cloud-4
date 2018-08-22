package com.ssitcloud.dblib.entity;


/**
 * 上传读者数据的实体类
 * 对应libraryinfo库的 readerdata表
 *
 * <p>2017年9月13日 下午1:44:00  
 * @author hjc 
 *
 */
public class ReaderDataEntity {
	
	/**
	 * 
		//读者数据
		0	lib_id：	图书馆编号
		1	card_no：	读者卡号
		2	card_type：	读者卡类型
		3	valid：	是否有效
		4	expire_date：	过期时间
		5	privilege_fee：	需交押金费用
		6	name：	名称
		7	password：	密码
		8	id_card：	身份证号
		9	birth：	生日
		10	gender：	性别1男0女
		11	address：	住址
		12	age：	年龄
		13	login_name：	登录名
		14	allown_loancount：	最大借阅数
		15	surplus_count：	剩余可借数
		16	advance_charge：	预付款
		17	deposit：	押金
		18	arrearage：	欠款
	 */
	
	private Integer recno;//int(11) NOT NULL自增记录号
	private Integer lib_idx;//int(11) NOT NULL图书馆idx
	private String card_no;//varchar(30) NULL读者证号
	private String card_type;//varchar(10) NULL读者类型
	private String valid;//varchar(5) NULL是否有效
	private String expire_date;//varchar(30) NULL过期时间
	private String privilege_fee;//varchar(20) NULL 需交押金费用
	private String name;//varchar(100) NULL名称
	private String password;//varchar(200) NULL证密码
	private String id_card;//varchar(20) NULL身份证号
	private String birth;//varchar(20) NULL生日
	private String gender;//varchar(1) NULL性别1男0女
	private String address;//varchar(300) NULL住址
	private Integer age;//int(11) NULL年龄
	private String login_name;//varchar(50) NULL登录名
	private Integer allown_loancount;//int(11) NULL最大借阅数
	private Integer surplus_count;//int(11) NULL剩余可借数
	private String advance_charge;//varchar(20) NULL预付款
	private String deposit;//varchar(20) NULL押金
	private String arrearage;//varchar(20) NULL欠款
	private String create_time;//varchar(20) NOT NULL创建日期
	private String update_time;//varchar(20) NULL修改日期
	public Integer getRecno() {
		return recno;
	}
	public void setRecno(Integer recno) {
		this.recno = recno;
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
	public String getCard_type() {
		return card_type;
	}
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	public String getPrivilege_fee() {
		return privilege_fee;
	}
	public void setPrivilege_fee(String privilege_fee) {
		this.privilege_fee = privilege_fee;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
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
	public String getAdvance_charge() {
		return advance_charge;
	}
	public void setAdvance_charge(String advance_charge) {
		this.advance_charge = advance_charge;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getArrearage() {
		return arrearage;
	}
	public void setArrearage(String arrearage) {
		this.arrearage = arrearage;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
	
	
}
