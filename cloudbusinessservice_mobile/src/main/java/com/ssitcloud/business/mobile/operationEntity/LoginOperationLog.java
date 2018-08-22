package com.ssitcloud.business.mobile.operationEntity;
public class LoginOperationLog extends AbstractOperationLog {
	private String loginType="app";
	private String login_name;
	private Integer fail_time = Integer.valueOf(0);
	
	@Override
	public String getOperation_cmd() {
		return "02020203";
	}

	@Override
	public String getOperation() {
		return "|"+loginType+"|"+login_name+"|"+fail_time+"|";
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public Integer getFail_time() {
		return fail_time;
	}

	public void setFail_time(Integer fail_time) {
		this.fail_time = fail_time;
	}
	
}
