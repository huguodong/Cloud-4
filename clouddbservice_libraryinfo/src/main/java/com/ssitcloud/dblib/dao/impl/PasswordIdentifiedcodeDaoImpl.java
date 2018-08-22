package com.ssitcloud.dblib.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.dblib.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dblib.dao.PasswordIdentifiedcodeDao;
import com.ssitcloud.dblib.entity.PasswordIdentifiedcodeEntity;

@Repository
public class PasswordIdentifiedcodeDaoImpl extends CommonDaoImpl implements PasswordIdentifiedcodeDao {

	@Override
	public boolean insert(PasswordIdentifiedcodeEntity entity) {
		int i = this.sqlSessionTemplate.insert("passwordIdentifiedcode.insert", entity);
		return i>0?true:false;
	}

	@Override
	public PasswordIdentifiedcodeEntity selectCode(int idx) {
		return this.sqlSessionTemplate.selectOne("passwordIdentifiedcode.selectCode", idx);
	}
	
	@Override
	public void deleteByPk(int idx) {
		this.sqlSessionTemplate.delete("passwordIdentifiedcode.deleteByPk", idx);
	}
}
