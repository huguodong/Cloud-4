package com.ssit.Entity;

public class PatronEntity {
	private String type;
	private long in_total;
	private long out_total;
	private long here_total;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getIn_total() {
		return in_total;
	}

	public void setIn_total(long in_total) {
		this.in_total = in_total;
	}

	public long getOut_total() {
		return out_total;
	}

	public void setOut_total(long out_total) {
		this.out_total = out_total;
	}

	public long getHere_total() {
		return here_total;
	}

	public void setHere_total(long here_total) {
		this.here_total = here_total;
	}

}
