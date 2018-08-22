package com.ssitcloud.business.mobile.operationEntity;
public class UnBindCardOperationLog extends AbstractOperationLog{
	private String reader_idx;
	private String login_name;
	private String card_no;
	private String lib_idx;
	
	
	@Override
	public String getOperation_cmd() {
		return "02020209";
	}

	@Override
	public String getOperation() {
		return "|"+lib_idx+"|"+reader_idx+"|"+login_name+"|"+card_no;
	}

	public String getReader_idx() {
		return reader_idx;
	}

	public void setReader_idx(String reader_idx) {
		this.reader_idx = reader_idx;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public String getLib_idx() {
		return lib_idx;
	}

	public void setLib_idx(String lib_idx) {
		this.lib_idx = lib_idx;
	}
}
