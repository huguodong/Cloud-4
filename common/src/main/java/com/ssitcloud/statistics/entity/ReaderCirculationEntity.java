package com.ssitcloud.statistics.entity;



/**
 * 读者维度统计统计mapping
 * @author yyl
 *2017年12月11日  14：55：10
 */
public class ReaderCirculationEntity{

	private String id;
	private String validAuthcardNo;//读者证号码
	private String id_number;//身份证号码
	private String reader_name;//读者姓名
	private String readerType;//本科生、硕士生、博士生、教工,便于分组统计数据
	private String reader_sex;//读者性别
	private String address;//地址
	private String birth_date;//出生日期
	private String area;//地区
	private String grade;//年级
	private String education;//学历
	private String profession;//专业
	private String academy;//学院
	private String department;//系别
	private String schoolingLength;//学制
	private String admission_date;//入学日期
	private String graduation_date;//毕业日期
	private String lib_idx;//图书馆ID
	private String lib_id;//图书馆ID
	private String lib_name;//图书馆名称
	private String opercmd;//操作
	private String opertime;//操作时间
	private Integer count;//数量
	private String library_idx ;//图书馆idx
	private Integer device_idx;//
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValidAuthcardNo() {
		return validAuthcardNo;
	}
	public void setValidAuthcardNo(String validAuthcardNo) {
		this.validAuthcardNo = validAuthcardNo;
	}
	public String getId_number() {
		return id_number;
	}
	public void setId_number(String id_number) {
		this.id_number = id_number;
	}
	public String getReader_name() {
		return reader_name;
	}
	public void setReader_name(String reader_name) {
		this.reader_name = reader_name;
	}
	public String getReader_sex() {
		return reader_sex;
	}
	public void setReader_sex(String reader_sex) {
		this.reader_sex = reader_sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getSchoolingLength() {
		return schoolingLength;
	}
	public void setSchoolingLength(String schoolingLength) {
		this.schoolingLength = schoolingLength;
	}
	public String getAdmission_date() {
		return admission_date;
	}
	public void setAdmission_date(String admission_date) {
		this.admission_date = admission_date;
	}
	public String getGraduation_date() {
		return graduation_date;
	}
	public void setGraduation_date(String graduation_date) {
		this.graduation_date = graduation_date;
	}
	public String getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(String lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getLib_id() {
		return lib_id;
	}
	public void setLib_id(String lib_id) {
		this.lib_id = lib_id;
	}
	public String getLib_name() {
		return lib_name;
	}
	public void setLib_name(String lib_name) {
		this.lib_name = lib_name;
	}
	public String getOpercmd() {
		return opercmd;
	}
	public void setOpercmd(String opercmd) {
		this.opercmd = opercmd;
	}
	public String getOpertime() {
		return opertime;
	}
	public void setOpertime(String opertime) {
		this.opertime = opertime;
	}
	public String getReaderType() {
		return readerType;
	}
	public void setReaderType(String readerType) {
		this.readerType = readerType;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public String getLibrary_idx() {
		return library_idx;
	}
	public void setLibrary_idx(String library_idx) {
		this.library_idx = library_idx;
	}
	public Integer getDevice_idx() {
		return device_idx;
	}
	public void setDevice_idx(Integer device_idx) {
		this.device_idx = device_idx;
	}	
	
	
	
}
