package com.ssitcloud.dblib.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.RegisterIdentifiedcodeDao;
import com.ssitcloud.dblib.entity.RegisterIdentifiedcodeEntity;

@Repository
public class RegisterIdentifiedcodeDaoImpl extends CommonDaoImpl implements RegisterIdentifiedcodeDao {

	@Override
	public boolean insert(RegisterIdentifiedcodeEntity entity) {
		int i = this.sqlSessionTemplate.insert("registerIdentifiedcode.insert", entity);
		return i>0?true:false;
	}

	@Override
	public RegisterIdentifiedcodeEntity selectCode(String email) {
		return this.sqlSessionTemplate.selectOne("registerIdentifiedcode.selectCode", email);
	}

	@Override
	public void deleteByPk(String email) {
		this.sqlSessionTemplate.delete("registerIdentifiedcode.deleteByPk", email);
		
	}

}
