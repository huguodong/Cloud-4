package com.ssitcloud.operlog.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 流通记录
 * 
 * @author Administrator
 * 
 */
@JsonInclude(value = Include.NON_NULL)
@Document(collection = "loan_log")
public class LoanLogEntity {
	@Id
	private String id;
	/**
	 * 流通信息
	 */
	// 流通记录操作时间
	@Field(value = "opertime")
	private String operTime;
	// 操作人（读者卡号）
	@Field(value = "operator")
	private String operator;
	// 操作命令
	/**
	 * 借还标志1-借书，2-还书，3-续借,4-取预借书，5-取消借书（借书失败做还回），6-取消取预借书（取预借书失败做还回
	 */
	@Field(value = "opercmd")
	private String operCmd;
	/**
	 * 操作结果 成功1 失败0
	 */
	@Field(value = "operresult")
	private String operResult;
	// 认证卡号
	@Field(value = "AuthCardno")
	private String authCardno;
	// 认证类型
	@Field(value = "AuthType")
	private String authType;
	// 借书书架号
	@Field(value = "ItemLocation")
	private String itemLocation;
	// 还书书箱号
	@Field(value = "ItemBin")
	private String itemBin;
	// 借书日期
	@Field(value = "ItemLoanDate")
	private String itemLoanDate;
	// 还书日期
	@Field(value = "ItemReturnDate")
	private String itemReturnDate;

	/**
	 * 图书信息
	 */
	// 条码号
	@Field(value = "Barcode")
	private String barCode;
	// 题名
	@Field(value = "Title")
	private String title;
	// 著者
	@Field(value = "Author")
	private String author;
	// ISBN
	@Field(value = "ISBN")
	private String ISBN;
	// 索取号
	@Field(value = "Callno")
	private String callno;
	// 分类号
	@Field(value = "Classno")
	private String classNo;
	// 图书类型 1纸质图书　2光盘
	@Field(value = "MediaType")
	private String mediaType;
	// 图书价格
	@Field(value = "Price")
	private String price;
	// 图书流通类型
	@Field(value = "CirculationType")
	private String circulationType;
	// 图书所属馆藏地
	@Field(value = "PermanentLocation")
	private String permanentLocation;
	// 图书当前馆藏地
	@Field(value = "CurrentLocation")
	private String currentLocation;
	// 出版社
	@Field(value = "Publisher")
	private String publisher;
	// 主题词
	@Field(value = "Subject")
	private String subject;
	// 书总页数
	@Field(value = "PageNum")
	private String pageNum;
	/**
	 * 读者信息
	 */
	@Field(value = "name")
	private String name;// 读者姓名

	// @Field(value="cardNo")
	// private String cardNo;//读者卡号,operator就是读者卡号

	@Field(value = "otherNo")
	private String otherNo;// 其他卡号

	// @Field(value="IdCard")
	// private String IdCard;//证件号 authCardno

	// @Field(value="IdCardType")
	// private String IdCardType;//证件类型1身份证　2社保卡3护照4港澳通行证

	@Field(value = "libId")
	private String libId;// 图书馆ID

	@Field(value = "password")
	private String password;// 密码？

	@Field(value = "state")
	private String state;// 使用状态

	@Field(value = "birthday")
	private String birthday;// 生日

	@Field(value = "age")
	private int age; // 年龄

	@Field(value = "sex")
	private String sex;// 性别

	@Field(value = "cirType")
	private String cirType;// 读者流通类型

	@Field(value = "cirName")
	private String cirName;// 读者流通类型名称

	@Field(value = "diposit")
	private String diposit;// 押金

	@Field(value = "address")
	private String address;// 通信地址

	@Field(value = "email")
	private String email;//

	@Field(value = "phone")
	private String phone;// 电话

	@Field(value = "telphone")
	private String telphone;// 手机

	@Field(value = "nationality")
	private String nationality;// 民族

	@Field(value = "mark")
	private String mark;// 备注

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperTime() {
		return operTime;
	}

	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperCmd() {
		return operCmd;
	}

	public void setOperCmd(String operCmd) {
		this.operCmd = operCmd;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getAuthCardno() {
		return authCardno;
	}

	public void setAuthCardno(String authCardno) {
		this.authCardno = authCardno;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getItemLocation() {
		return itemLocation;
	}

	public void setItemLocation(String itemLocation) {
		this.itemLocation = itemLocation;
	}

	public String getItemBin() {
		return itemBin;
	}

	public void setItemBin(String itemBin) {
		this.itemBin = itemBin;
	}

	public String getItemLoanDate() {
		return itemLoanDate;
	}

	public void setItemLoanDate(String itemLoanDate) {
		this.itemLoanDate = itemLoanDate;
	}

	public String getItemReturnDate() {
		return itemReturnDate;
	}

	public void setItemReturnDate(String itemReturnDate) {
		this.itemReturnDate = itemReturnDate;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getCallno() {
		return callno;
	}

	public void setCallno(String callno) {
		this.callno = callno;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCirculationType() {
		return circulationType;
	}

	public void setCirculationType(String circulationType) {
		this.circulationType = circulationType;
	}

	public String getPermanentLocation() {
		return permanentLocation;
	}

	public void setPermanentLocation(String permanentLocation) {
		this.permanentLocation = permanentLocation;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOtherNo() {
		return otherNo;
	}

	public void setOtherNo(String otherNo) {
		this.otherNo = otherNo;
	}

	public String getLibId() {
		return libId;
	}

	public void setLibId(String libId) {
		this.libId = libId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getCirType() {
		return cirType;
	}

	public void setCirType(String cirType) {
		this.cirType = cirType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCirName() {
		return cirName;
	}

	public void setCirName(String cirName) {
		this.cirName = cirName;
	}

	public String getDiposit() {
		return diposit;
	}

	public void setDiposit(String diposit) {
		this.diposit = diposit;
	}

}
