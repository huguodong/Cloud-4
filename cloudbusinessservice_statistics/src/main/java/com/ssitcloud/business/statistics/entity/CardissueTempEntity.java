package com.ssitcloud.business.statistics.entity;

public class CardissueTempEntity {
	private String[] date;
	private long[] success;
	private long[] failure;

	public String[] getDate() {
		return date;
	}

	public void setDate(String[] date) {
		this.date = date;
	}

	public long[] getSuccess() {
		return success;
	}

	public void setSuccess(long[] success) {
		this.success = success;
	}

	public long[] getFailure() {
		return failure;
	}

	public void setFailure(long[] failure) {
		this.failure = failure;
	}

	

}
