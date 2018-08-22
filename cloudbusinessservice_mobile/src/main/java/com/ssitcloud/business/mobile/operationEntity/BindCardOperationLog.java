package com.ssitcloud.business.mobile.operationEntity;
public class BindCardOperationLog extends AbstractOperationLog{
	private Integer reader_idx;
	private String login_name;
	private String card_no;
	private Integer lib_idx;
	
	
	@Override
	public String getOperation_cmd() {
		return "02020208";
	}

	@Override
	public String getOperation() {
		return "|"+lib_idx+"|"+reader_idx+"|"+login_name+"|"+card_no;
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

	public String getCard_no() {
		return card_no;
	}

	public void setCard_no(String card_no) {
		this.card_no = card_no;
	}

	public Integer getLib_idx() {
		return lib_idx;
	}

	public void setLib_idx(Integer lib_idx) {
		this.lib_idx = lib_idx;
	}
	
}
