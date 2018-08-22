package com.ssitcloud.dblib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.RegisterIdentifiedcodeDao;
import com.ssitcloud.dblib.entity.RegisterIdentifiedcodeEntity;
import com.ssitcloud.dblib.service.RegisterIdentifiedcodeService;

@Service
public class RegisterIdentifiedcodeServiceImpl implements RegisterIdentifiedcodeService {
	
	@Autowired
	private RegisterIdentifiedcodeDao dao;
	
	@Override
	public boolean insert(RegisterIdentifiedcodeEntity entity) {
		return dao.insert(entity);
	}

	@Override
	public RegisterIdentifiedcodeEntity selectCode(String email) {
		return dao.selectCode(email);
	}

	@Override
	public void deleteByPk(String email) {
		dao.deleteByPk(email);
	}

}
