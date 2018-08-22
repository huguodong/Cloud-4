package com.ssitcloud.dblib.entity;

import java.sql.Timestamp;

/**
 * electronic_certificate
 * @author LXP
 * @version 创建时间：2017年2月28日 上午9:21:47
 */
public class ElectronicCertificateEntity {
	private Integer electronic_idx;//id
	private Integer lib_idx;//图书馆idx
	private String electronic_type;//varchar(10) 操作类型 1借书 2还书 3续借 4交押金 5交预付款 6交欠款 7从预付款扣除
	private String cardno;//varchar(50) 读者证号
	private String barno;//varchar(50) 图书条码
	private String title;//varchar(300) 书名
	private Double fine;// 费用
	private String purpose;// 存款用途 1押金 2欠款 3预付款
	private Timestamp control_time;// 处理时间
	private String control_time_str;//时间的字符串形式
	private Timestamp return_date;//应还日期，对应借书还书续借操作时
	private Integer electronic_state;//信息状态，默认0未读，1已读
	
	public Integer getElectronic_idx() {
		return electronic_idx;
	}
	public void setElectronic_idx(Integer electronic_idx) {
		this.electronic_idx = electronic_idx;
	}
	public Integer getLib_idx() {
		return lib_idx;
	}
	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	public String getElectronic_type() {
		return electronic_type;
	}
	public void setElectronic_type(String electronic_type) {
		this.electronic_type = electronic_type;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getBarno() {
		return barno;
	}
	public void setBarno(String barno) {
		this.barno = barno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getFine() {
		return fine;
	}
	public void setFine(Double fine) {
		this.fine = fine;
	}
	public Timestamp getControl_time() {
		return control_time;
	}
	public void setControl_time(Timestamp control_time) {
		this.control_time = control_time;
	}
	public String getControl_time_str() {
		return control_time_str;
	}
	public void setControl_time_str(String control_time_str) {
		this.control_time_str = control_time_str;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public Timestamp getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Timestamp return_date) {
		this.return_date = return_date;
	}
	public Integer getElectronic_state() {
		return electronic_state;
	}
	public void setElectronic_state(Integer electronic_state) {
		this.electronic_state = electronic_state;
	}

}
