package com.ssitcloud.dbauth.entity;

import java.sql.Timestamp;

/** 
 * 历史密码表实体类
 * <p>2016年3月29日 下午5:24:46  
 * @author hjc 
 *
 */
public class PasswordHistoryEntity {
	/** 自增ID */
	private Integer password_idx;
	/** 操作员表ID */
	private Integer operator_idx;
	/** 历史密码  */
	private String password;
	/** 修改时间 */
	private Timestamp modifyTime;


	public Integer getPassword_idx() {
		return password_idx;
	}

	public void setPassword_idx(Integer password_idx) {
		this.password_idx = password_idx;
	}

	public Integer getOperator_idx() {
		return operator_idx;
	}

	public void setOperator_idx(Integer operator_idx) {
		this.operator_idx = operator_idx;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
