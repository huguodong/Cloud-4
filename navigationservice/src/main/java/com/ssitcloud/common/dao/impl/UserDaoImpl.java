package com.ssitcloud.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.UserDao;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;

@Repository
public class UserDaoImpl extends CommonDaoImpl implements UserDao {

	@Override
	public Operator logincheck(UserEntity user) {
		return this.sqlSessionTemplate.selectOne("operator.logincheck", user);
	}

	@Override
	public Integer changePassword(UserEntity user) {
		return this.sqlSessionTemplate.update("operator.changePassword", user);
	}

}
