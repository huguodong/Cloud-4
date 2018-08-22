package com.ssitcloud.dbstatistics.entity;

public class CirculationTempEntity {
	private String[] date;
	private long[] loan_total;
	private long[] return_total;
	private long[] renew_total;
	public String[] getDate() {
		return date;
	}
	public void setDate(String[] date) {
		this.date = date;
	}
	public long[] getLoan_total() {
		return loan_total;
	}
	public void setLoan_total(long[] loan_total) {
		this.loan_total = loan_total;
	}
	public long[] getReturn_total() {
		return return_total;
	}
	public void setReturn_total(long[] return_total) {
		this.return_total = return_total;
	}
	public long[] getRenew_total() {
		return renew_total;
	}
	public void setRenew_total(long[] renew_total) {
		this.renew_total = renew_total;
	}
	
	
}
