package com.ssitcloud.readermgmt.entity;

import java.util.Date;

public class CollegeInfoEntity {
	
	/**实际读者证号*/
	private String actual_card_no;
	private String reader_type;
	/**身份证*/
	private String id_card;
	/**专业*/
	private String profession;
	/**学历*/
	private String education;
	/**学院/部门*/
	private String academy;
	/**系*/
	private String department;
	/**学制*/
	private String schoolingLength;
	/**年级*/
	private String grade;
	/**入学日期*/
	private Date admission_date;
	/**毕业日期*/
	private Date graduation_date;
	
	private Integer lib_idx;
	
	public String getActual_card_no() {
		return actual_card_no;
	}
	public void setActual_card_no(String actual_card_no) {
		this.actual_card_no = actual_card_no;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
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
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
	public Date getAdmission_date() {
		return admission_date;
	}
	public void setAdmission_date(Date admission_date) {
		this.admission_date = admission_date;
	}
	public Date getGraduation_date() {
		return graduation_date;
	}
	public void setGraduation_date(Date graduation_date) {
		this.graduation_date = graduation_date;
	}
	public String getReader_type() {
		return reader_type;
	}
	public void setReader_type(String reader_type) {
		this.reader_type = reader_type;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	
	
	
	
	
	


	
	
}
