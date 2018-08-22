package com.ssitcloud.business.statistics.entity;

public class VisitorTempEntity {
	private String[] date;
	private long[] in_total;
	private long[] out_total;

	public String[] getDate() {
		return date;
	}

	public void setDate(String[] date) {
		this.date = date;
	}

	public long[] getIn_total() {
		return in_total;
	}

	public void setIn_total(long[] in_total) {
		this.in_total = in_total;
	}

	public long[] getOut_total() {
		return out_total;
	}

	public void setOut_total(long[] out_total) {
		this.out_total = out_total;
	}
}
