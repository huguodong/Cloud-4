package com.ssitcloud.dblib.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssitcloud.dblib.dao.PasswordIdentifiedcodeDao;
import com.ssitcloud.dblib.entity.PasswordIdentifiedcodeEntity;
import com.ssitcloud.dblib.service.PasswordIdentifiedcodeService;

@Service
public class PasswordIdentifiedcodeServiceImpl implements PasswordIdentifiedcodeService {

	@Autowired
	private PasswordIdentifiedcodeDao dao;

	@Override
	public boolean insert(PasswordIdentifiedcodeEntity entity) {
		return dao.insert(entity);
	}

	@Override
	public PasswordIdentifiedcodeEntity selectCode(int idx) {
		return dao.selectCode(idx);
	}

	@Override
	public void deleteByPk(int idx) {
		dao.deleteByPk(idx);
	}

}
