package com.ssitcloud.dblib.service;

import com.ssitcloud.dblib.entity.PasswordIdentifiedcodeEntity;

public interface PasswordIdentifiedcodeService {
	
	boolean insert(PasswordIdentifiedcodeEntity entity);
	
	PasswordIdentifiedcodeEntity selectCode(int idx);
	
	void deleteByPk(int idx);
}
