package com.ssitcloud.statistics.entity;

import io.searchbox.annotations.JestId;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


/**
 * {
 "opertime":"2017-04-20 14:09:00",
 "operator":"0440050000204",
 "opercmd":"00010203",
 "operresult":"1",
 "errorcode":"",
 "AuthCardno":"0440050000204",
 "AuthType":"0",
 "TransDate":"2017-04-20 14:9:0",
 "Name":"李卫珊",
 "Deposit":"0.00",
 "PrivilegeFee":"0.00",
 "Cardtype":"0",
 "Address":"",
 "Telephone":"",
 "Mobile":"",
 "E-Mail":"",
 "Birth":"",
 "Gender":"",
 "ExpireDate":"",
 "EthnicGroup":"",
 "Education":"",
 "Profession":"",
 "Barcode":"04400510108696",
 "Title":"N字操盘法:N字理论实战与活用",
 "Author":"",
 "ISBN":"",
 "Callno":"",
 "Price":0,
 "CirculationType":"",
 "PermanentLocation":"",
 "CurrentLocation":"SSL0002",
 "Publisher":"",
 "Subject":"",
 "PageNum":"",
 "CirculStatus":"",
 "ItemRenewDate":"2017-04-20 14:9:0",
 "createTime":"20170421095759"
 }

 {
 "opertime":"2017-04-20 14:03:16",
 "operator":"",
 "opercmd":"00010202",
 "operresult":"0",
 "errorcode":"",
 "AuthCardno":"",
 "AuthType":"0",
 "TransDate":"2017-04-20 14:3:16",
 "Name":"",
 "Deposit":"",
 "PayFee":"",
 "PrivilegeFee":"",
 "Cardtype":"0",
 "Address":"",
 "Telephone":"",
 "Mobile":"",
 "E-Mail":"",
 "Birth":"",
 "Gender":"",
 "ExpireDate":"",
 "EthnicGroup":"",
 "Education":"",
 "Profession":"",
 "Barcode":"04400510108696",
 "Title":"N字操盘法:N字理论实战与活用",
 "Author":"",
 "ISBN":"",
 "Callno":"",
 "Price":0,
 "CirculationType":"",
 "PermanentLocation":"",
 "CurrentLocation":"SSL0002",
 "Publisher":"",
 "Subject":"",
 "PageNum":"",
 "CirculStatus":"",
 "ItemLoanDate":"2017-04-20 14:3:16",
 "ItemLocation":"",
 "createTime":"20170421095758"
 }
 {
 "opertime":"2017-04-20 14:06:29",
 "operator":"0440050000204",
 "opercmd":"00010203",
 "operresult":"1",
 "errorcode":"",
 "AuthCardno":"0440050000204",
 "AuthType":"0",
 "TransDate":"2017-04-20 14:6:29",
 "Name":"李卫珊",
 "Deposit":"0.00",
 "PrivilegeFee":"0.00",
 "Cardtype":"0",
 "Address":"",
 "Telephone":"",
 "Mobile":"",
 "E-Mail":"",
 "Birth":"",
 "Gender":"",
 "ExpireDate":"",
 "EthnicGroup":"",
 "Education":"",
 "Profession":"",
 "Barcode":"04400510737540",
 "Title":"冷历史",
 "Author":"",
 "ISBN":"",
 "Callno":"",
 "Price":0,
 "CirculationType":"",
 "PermanentLocation":"",
 "CurrentLocation":"SSL0002",
 "Publisher":"",
 "Subject":"",
 "PageNum":"",
 "CirculStatus":"",
 "ItemRenewDate":"2017-04-20 14:6:29",
 "createTime":"20170421095758"
 }
 *
 * <p>2017年4月21日 下午3:29:05
 * @author hjc
 *
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class LoanLogEntity {

    @JestId
    private String id;

    private String opertime;
    private String operator;
    private String opercmd;
    private String operresult;

    @JsonProperty("AuthCardno")
    private String AuthCardno;

    @JsonProperty("AuthType")
    private String AuthType;

    @JsonProperty("TransDate")
    private String TransDate;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Deposit")
    private double Deposit;

    @JsonProperty("PrivilegeFee")
    private double PrivilegeFee;

    @JsonProperty("Cardtype")
    private String Cardtype;

    @JsonProperty("Address")
    private String Address;

    @JsonProperty("Telephone")
    private String Telephone;

    @JsonProperty("Mobile")
    private String Mobile;

    @JsonProperty("E-Mail")
    private String EMail;

    @JsonProperty("Birth")
    private String Birth;

    @JsonProperty("Gender")
    private String Gender;

    @JsonProperty("ExpireDate")
    private String ExpireDate;

    @JsonProperty("EthnicGroup")
    private String EthnicGroup;    //民族

    @JsonProperty("Education")
    private String Education;

    @JsonProperty("Profession")
    private String Profession;


	@JsonProperty("ItemBin")
	private String ItemBin;


    @JsonProperty("Barcode")
    private String Barcode;

    @JsonProperty("Title")
    private String Title;

    @JsonProperty("Author")
    private String Author;

    @JsonProperty("ISBN")
    private String ISBN;

    @JsonProperty("Callno")
    private String Callno;

//	@JsonProperty("MediaType")
//	private String MediaType;

    @JsonProperty("Price")
    private String Price;

    @JsonProperty("CirculationType")
    private String CirculationType;

    @JsonProperty("PermanentLocation")
    private String PermanentLocation;

    @JsonProperty("CurrentLocation")
    private String CurrentLocation;

    @JsonProperty("Publisher")
    private String Publisher;

    @JsonProperty("Subject")
    private String Subject;

    @JsonProperty("PageNum")
    private String PageNum;

    @JsonProperty("CirculStatus")
    private String CirculStatus;

//    @JsonProperty("ItemRenewDate")
//    private String ItemRenewDate;
//
//    @JsonProperty("ItemLoanDate")
//    private String ItemLoanDate;
    
    @JsonProperty("ItemReturnDate")
    private String ItemReturnDate;

    @JsonProperty("ItemLocation")
    private String ItemLocation;

    @JsonProperty("PayFee")
    private double PayFee;


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

    private String callNumber;//索书号大类

    public String getItemReturnDate() {
		return ItemReturnDate;
	}

	public void setItemReturnDate(String itemReturnDate) {
		ItemReturnDate = itemReturnDate;
	}

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


    public String getBarcode() {
        return Barcode;
    }

    public void setBarcode(String barcode) {
        Barcode = barcode;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    public String getCallno() {
        return Callno;
    }

    public void setCallno(String callno) {
        Callno = callno;
    }


    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCirculationType() {
        return CirculationType;
    }

    public void setCirculationType(String circulationType) {
        CirculationType = circulationType;
    }

    public String getPermanentLocation() {
        return PermanentLocation;
    }

    public void setPermanentLocation(String permanentLocation) {
        PermanentLocation = permanentLocation;
    }

    public String getCurrentLocation() {
        return CurrentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        CurrentLocation = currentLocation;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public String getPageNum() {
        return PageNum;
    }

    public void setPageNum(String pageNum) {
        PageNum = pageNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getDeposit() {
        return Deposit;
    }

    public void setDeposit(double deposit) {
        Deposit = deposit;
    }

    public double getPrivilegeFee() {
        return PrivilegeFee;
    }

    public void setPrivilegeFee(double privilegeFee) {
        PrivilegeFee = privilegeFee;
    }

    public String getCardtype() {
        return Cardtype;
    }

    public void setCardtype(String cardtype) {
        Cardtype = cardtype;
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

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String eMail) {
        EMail = eMail;
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

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getEthnicGroup() {
        return EthnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        EthnicGroup = ethnicGroup;
    }

    public String getEducation() {
        return Education;
    }

    public void setEducation(String education) {
        Education = education;
    }

    public String getProfession() {
        return Profession;
    }

    public void setProfession(String profession) {
        Profession = profession;
    }

    public String getCirculStatus() {
        return CirculStatus;
    }

    public void setCirculStatus(String circulStatus) {
        CirculStatus = circulStatus;
    }

    public String getOpertime() {
        return opertime;
    }

    public void setOpertime(String opertime) {
        this.opertime = opertime;
    }

    public String getTransDate() {
        return TransDate;
    }

    public void setTransDate(String transDate) {
        TransDate = transDate;
    }


    public String getItemBin() {
		return ItemBin;
	}

	public void setItemBin(String itemBin) {
		ItemBin = itemBin;
	}

	public String getItemLocation() {
        return ItemLocation;
    }

    public void setItemLocation(String itemLocation) {
        ItemLocation = itemLocation;
    }

    public double getPayFee() {
        return PayFee;
    }

    public void setPayFee(double payFee) {
        PayFee = payFee;
    }

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCallNumber() {
		return callNumber;
	}

	public void setCallNumber(String callNumber) {
		this.callNumber = callNumber;
	}
    

}
