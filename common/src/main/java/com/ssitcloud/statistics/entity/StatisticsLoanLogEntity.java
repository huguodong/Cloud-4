package com.ssitcloud.statistics.entity;

public class StatisticsLoanLogEntity {
	private String type;
	private long loan_total;
	private long return_total;
	private long renew_total;
	private String time;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getLoan_total() {
		return loan_total;
	}

	public void setLoan_total(long loan_total) {
		this.loan_total = loan_total;
	}

	public long getReturn_total() {
		return return_total;
	}

	public void setReturn_total(long return_total) {
		this.return_total = return_total;
	}

	public long getRenew_total() {
		return renew_total;
	}

	public void setRenew_total(long renew_total) {
		this.renew_total = renew_total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
