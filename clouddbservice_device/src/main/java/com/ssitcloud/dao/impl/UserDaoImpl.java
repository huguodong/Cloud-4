package com.ssitcloud.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssitcloud.common.dao.impl.CommonDaoImpl;
import com.ssitcloud.dao.UserDao;
import com.ssitcloud.entity.UserRolePermessionEntity;
@Repository
public class UserDaoImpl extends CommonDaoImpl implements UserDao{
	
	@Override
	public List<UserRolePermessionEntity> querybyId(UserRolePermessionEntity userRolePermession){
		List<UserRolePermessionEntity> list = selectList("reloperatorgroup.queryRoleAndPermession", userRolePermession);
		return list;
	}

	@Override
	public List<UserRolePermessionEntity> queryAll() {
		return getSqlSession().selectList("reloperatorgroup.queryAll");
	}
	
}
