package com.ssitcloud.dblib.dao;

import com.ssitcloud.dblib.entity.RegisterIdentifiedcodeEntity;

public interface RegisterIdentifiedcodeDao {
	boolean insert(RegisterIdentifiedcodeEntity entity);
	
	RegisterIdentifiedcodeEntity selectCode(String email);
	
	void deleteByPk(String email);
}
