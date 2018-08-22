package com.ssitcloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssitcloud.dao.UserDao;
import com.ssitcloud.entity.UserRolePermessionEntity;
import com.ssitcloud.service.UserService;
@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	@Override
	public List<UserRolePermessionEntity> queryById(String id){
		if(id==null||"".equals(id))return null;
		return userDao.querybyId(new UserRolePermessionEntity(Integer.parseInt(id)));
	}
	
	@Override
	public List<UserRolePermessionEntity> queryByIdAndDeviceUser(UserRolePermessionEntity userRolePermession){
		return userDao.querybyId(userRolePermession);
	}

	@Override
	public List<UserRolePermessionEntity> queryAll() {
		return userDao.queryAll();
	}

	@Override
	public List<UserRolePermessionEntity> queryByUserRolePermessionEntity(
			UserRolePermessionEntity userRolePermession) {
		return userDao.querybyId(userRolePermession);
	}
	
}
