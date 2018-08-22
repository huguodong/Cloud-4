package com.ssitcloud.business.mobile.operationEntity;

/**
 * 用户注册操作日志
 * @author LXP
 * @version 创建时间：2017年3月4日 下午4:42:26
 */
public class RegisterOperationLog extends AbstractOperationLog {
	private String operation;
	private String login_name;
	
	public void setOperation(String operation) {
		this.operation = operation;
	}

	@Override
	public String getOperation_cmd() {
		return "02020201";
	}

	@Override
	public String getOperation() {
		return "||"+login_name+"||";
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

}
