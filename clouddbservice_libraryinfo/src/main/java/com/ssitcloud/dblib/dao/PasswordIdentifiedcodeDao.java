package com.ssitcloud.dblib.dao;

import com.ssitcloud.dblib.entity.PasswordIdentifiedcodeEntity;

public interface PasswordIdentifiedcodeDao {
	boolean insert(PasswordIdentifiedcodeEntity entity);
	
	PasswordIdentifiedcodeEntity selectCode(int idx);
	
	void deleteByPk(int idx);
}
