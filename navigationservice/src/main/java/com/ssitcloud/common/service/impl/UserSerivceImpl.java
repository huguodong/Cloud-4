package com.ssitcloud.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.common.dao.UserDao;
import com.ssitcloud.common.entity.Operator;
import com.ssitcloud.common.entity.UserEntity;
import com.ssitcloud.common.service.UserService;

@Service
public class UserSerivceImpl implements UserService{
	@Resource
	private UserDao userDao;
	
	@Override
	public Operator logincheck(UserEntity user){
		return userDao.logincheck(user);
	}

	@Override
	public Integer changePassword(UserEntity user) {
		return userDao.changePassword(user);
	}
}
