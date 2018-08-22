package com.ssitcloud.business.mobile.operationEntity;
public class UpdateOperationLog extends AbstractOperationLog {
	private Integer reader_idx;
	private String login_name="";
	@Override
	public String getOperation_cmd() {
		return "02020205";
	}

	@Override
	public String getOperation() {
		return "|"+reader_idx+"|"+login_name+"||";
	}

	public Integer getReader_idx() {
		return reader_idx;
	}

	public void setReader_idx(Integer reader_idx) {
		this.reader_idx = reader_idx;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	
}
