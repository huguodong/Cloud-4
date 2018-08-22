package com.ssitcloud.dblib.service;

import com.ssitcloud.dblib.entity.RegisterIdentifiedcodeEntity;

public interface RegisterIdentifiedcodeService {
	
	boolean insert(RegisterIdentifiedcodeEntity entity);
	
	RegisterIdentifiedcodeEntity selectCode(String email);
	
	void deleteByPk(String email);
}
